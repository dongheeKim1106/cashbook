package com.gdu.cashbook1.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook1.mapper.BoardMapper;
import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.mapper.CommentMapper;
import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.mapper.MemberidMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;
import com.gdu.cashbook1.vo.Memberid;

@Service
@Transactional
public class MemberService {
	// 의존객체 자동주입
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	@Autowired
	private CashMapper cashMapper;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("D:\\spring_work\\maven.1590371302974\\cashbook\\src\\main\\resources\\static\\upload\\")
	private String path;
	
	public int getMemberPw(Member member) {
		// 랜덤 pw 추가
		UUID uuid = UUID.randomUUID();
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		System.out.println(row);
		if(row == 1) {
			System.out.println(memberPw + "<--updateMemberPw Service");
			// update 성공했으니 update 비밀번호를 메일로 전송
			// 메일객체 생성 new JavaMailSender(); 클래스
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			System.out.println(member.getMemberEmail());
			// 메일을 받는사람 주소
			simpleMailMessage.setTo(member.getMemberEmail());
			// 메일을 보내는사람 주소
			simpleMailMessage.setFrom("ehdgmlcm@gmail.com");
			// 메일 제목
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");
			// 메일 내용
			simpleMailMessage.setText("변경된 비밀번호는 " + memberPw + "입니다. 꼭 비밀번호 변경을 해주시기 바랍니다.");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	// 회원 전체 리스트 관리자
	public Map<String, Object> getMemberListAdmin(int currentPage, String searchWord) {
		System.out.println(searchWord);
		
		// 페이징을 위해서 필요한 값들을 map에 담아서 보내주기.
		int rowPerPage = 10;
		int beginRow = (currentPage-1)*rowPerPage;
		//일회용 맵
		Map<String, Object> map = new HashMap<>(); 
		map.put("searchWord", searchWord);
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		// 라스트페이지 출력
		int totalRow = memberMapper.getTotalRow(searchWord);
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage != 0) {
		   lastPage += 1;
		}
		
		List<Member> list = memberMapper.selectMemberList(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list", list);
		map2.put("lastPage", lastPage);
		
		return map2;
	}
	// 아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	// 회원 정보 수정
	public int modifyMember(MemberForm memberForm) {
		// 이미지 이름 불러오기
		String originMemberPic = memberMapper.selectMemberPic(memberForm.getMemberId());
		// 이미지 추가
		MultipartFile mf = memberForm.getMemberPic();
		System.out.println(mf + "<--MemberService:modifyMember mf");
		// 확장자 꺼내는 작업
		String originName = mf.getOriginalFilename();
		System.out.println(originName + "<--MemberService:modifyMember originName");
		
		String memberPic = "";
		// 값이 없으면 삭제 X
		if(!originName.equals("")) {
			// 이미지 삭제
			File originFile = new File(path + originMemberPic);
			// 초기설정 이미지 삭제 X
			if(originFile.exists() && !memberPic.equals("default.jpg")) {
				originFile.delete();
			}
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			memberPic = memberForm.getMemberId() + extension;
		} else {
			memberPic = originMemberPic;
		}
	
		// DB 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		System.out.println(member + "<-- MemberService.addMember:member");
		int row = memberMapper.updateMember(member);
		
		if(!originName.equals("")) {
			// 파일 저장
			// 경로 저장
			File file = new File(path + memberPic);
			// mf의 파일을 옮겨준다
			try {
				mf.transferTo(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 예외처리를 코드에 구현하지 않아도 문제 없는 예외
				throw new RuntimeException();
			}
			// 예외처리를 해야만 문법적으로 이상없는 예외
		}
		return row;
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		// 이미지 삭제
		// 파일 이름 가져오기
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		// 파일 삭제
		File file = new File(path + memberPic);
		// 초기설정 이미지 삭제 X
		if(file.exists() && !memberPic.equals("default.jpg")) {
			file.delete();
		}
		// 트랜잭션
		// 삽입
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		// 삭제
		commentMapper.deleteCommentByMember(loginMember.getMemberId());
		boardMapper.deleteBoardByMember(loginMember.getMemberId());
		cashMapper.deleteCashByMember(loginMember.getMemberId());
		memberMapper.deleteMember(loginMember);
	}
	// 회원정보 하나만 보여주기
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	// 아이디 비밀번호 보여주기
	public Member getMemberIdPw(String memberPhone) {
		return memberMapper.selectMemberIdPw(memberPhone);
	}
	// 아이디 비밀번호를 찾기위해 비밀번호 있나 확인
	public String findMember(String memberPhone) {
		return memberMapper.findMember(memberPhone);
	}
	// 아이디 중복체크
	public String checkMemberId(String memberIdCheck) {
		// id가 있으면 id가 리턴되고 없으면 null이 리턴된다
		return memberMapper.selectMemberId(memberIdCheck);
	}
	
	// login
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	// member 추가
	public int addMember(MemberForm memberForm) {
		
		MultipartFile mf = memberForm.getMemberPic();
		// 확장자 꺼내는 작업
		String originName = mf.getOriginalFilename();
		System.out.println(originName + "<--MemberService.addMember:originName");
		String memberPic = "";
		// 파일 첨부를 안할시에
		if(originName.equals("")) {
			memberPic = "default.jpg";
			
		} else {
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
		
			// 새로운 이름생성 이미지 이름을 ID 이름과 동일하게
			memberPic = memberForm.getMemberId() + extension;
		}
		// DB 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		System.out.println(member + "<-- MemberService.addMember:member");
		int row = memberMapper.insertMember(member);
		
		if(!originName.equals("")) {
			// 파일 저장
			// 경로 저장
			File file = new File(path + memberPic);
			// mf의 파일을 옮겨준다
			try {
				mf.transferTo(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 예외처리를 코드에 구현하지 않아도 문제 없는 예외
				throw new RuntimeException();
			}
			// 예외처리를 해야만 문법적으로 이상없는 예외
		}
		return row;
	}
}
