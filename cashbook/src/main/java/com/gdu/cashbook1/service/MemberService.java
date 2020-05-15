package com.gdu.cashbook1.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.mapper.MemberidMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
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
	private JavaMailSender javaMailSender;
	
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
	
	// 아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	// 회원 정보 수정
	public int modifyMember(Member member) {
		return memberMapper.updateMember(member);
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		// 트랜잭션
		// 삽입
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		// 삭제
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
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
