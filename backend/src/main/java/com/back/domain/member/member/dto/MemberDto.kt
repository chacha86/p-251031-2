package com.back.domain.member.member.dto

import com.back.domain.member.member.entity.Member
import java.time.LocalDateTime

// @JvmRecord 지우면 몇몇 테스트가 실패한다.
// 코틀린 직렬화시에는 is를 제거 하고 getter를 사용하기 때문에 isAdmin -> admin 으로 변환된다.
// 이를 유지하기 위해서는 @JvmRecord 어노테이션을 사용해야 한다.
@JvmRecord
data class MemberDto(
    val id: Long,
    val createDate: LocalDateTime,
    val modifyDate: LocalDateTime,
    val name: String,
    val isAdmin: Boolean
) {
    constructor(member: Member) : this(
        member.id,
        member.createDate,
        member.modifyDate,
        member.name,
        member.isAdmin
    )
}
