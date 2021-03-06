package com.gdu.cashbook1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;

@Mapper
public interface MemberMapper {
	// 회원 탈퇴
	public int deleteMemberAdmin(String memberId);
	// lastPage
	public int getTotalRow(String searchWord);
	// 관리자 전용 회원 리스트
	public List<Member> selectMemberList(Map<String, Object> map);
	// 이미지 이름 불러오기
	public String selectMemberPic(String memberId);
	// 비밀번호 바꾸기
	public int updateMemberPw(Member member);
	// 아이디 찾기
	public String selectMemberIdByMember(Member member);
	// 회원정보 수정
	public int updateMember(Member member);
	// 회원 탈퇴
	public int deleteMember(LoginMember loginMember);
	// 회원정보 하나씩 보기
	public Member selectMemberOne(LoginMember loginMember);
	// 전화번호가 있으면 아이디 비밀번호 보여줌
	public Member selectMemberIdPw(String memberPhone);
	// 전화번호가 있나 확인
	public String findMember(String memberPhone);
	// 아이디 중복 체크
	public String selectMemberId(String memberIdCheck);
	// login
	public LoginMember selectLoginMember(LoginMember loginMember);
	// member 추가
	public int insertMember(Member member);
}
