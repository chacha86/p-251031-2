package com.back.domain.member.member.service

import com.back.domain.member.member.entity.Member
import com.back.domain.member.member.repository.MemberRepository
import com.back.global.exception.ServiceException
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class MemberService(
    private val memberRepository: MemberRepository,
    private val authTokenService: AuthTokenService,
    private val passwordEncoder: PasswordEncoder
) {

    fun count(): Long {
        return memberRepository.count()
    }

    fun join(username: String, password: String, nickname: String): Member {
        return join(username, password, nickname, null)
    }

    fun join(username: String, password: String, nickname: String, profileImgUrl: String?): Member {
        memberRepository.findByUsername(username)
            .ifPresent { m: Member ->
                throw ServiceException("409-1", "이미 사용중인 아이디입니다.")
            }

        val member = Member(username, passwordEncoder.encode(password), nickname, profileImgUrl)
        return memberRepository.save(member)
    }


    fun modifyOrJoin(username: String, password: String, nickname: String, profileImgUrl: String?): Member {
        val member = memberRepository.findByUsername(username).orElse(null)
            ?: return join(username, password, nickname, profileImgUrl)

        member.update(nickname, profileImgUrl)

        return member
    }

    fun findByUsername(username: String): Optional<Member> {
        return memberRepository.findByUsername(username)
    }

    fun findByApiKey(apiKey: String): Optional<Member> {
        return memberRepository.findByApiKey(apiKey)
    }

    fun genAccessToken(member: Member): String {
        return authTokenService.genAccessToken(member)
    }

    // TODO: payload로 이름 변경 고민
    fun payloadOrNull(accessToken: String): Map<String, Any>? {
        return authTokenService.payloadOrNull(accessToken)
    }

    fun findById(id: Long): Optional<Member> {
        return memberRepository.findById(id)
    }

    fun findAll(): List<Member> {
        return memberRepository.findAll()
    }

    fun checkPassword(inputPassword: String, rawPassword: String) {
        if (!passwordEncoder.matches(inputPassword, rawPassword)) {
            throw ServiceException("401-2", "비밀번호가 일치하지 않습니다.")
        }
    }
}
