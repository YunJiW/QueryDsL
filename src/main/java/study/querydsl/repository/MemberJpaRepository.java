package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QTeam;

import java.util.Optional;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.*;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    //Querydsl 사용
    public List<Member> findAll_Querydsl(){
        return queryFactory.selectFrom(member).fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username",Member.class)
                .setParameter("username",username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryFactory.selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    //Builder 사용
    public List<MemberTeamDto> searchByBuilder(MemberSearchCond cond){

        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(cond.getUsername())){
            builder.and(member.username.eq(cond.getUsername()));
        }

        if(hasText(cond.getTeamName())){
            builder.and(team.name.eq(cond.getTeamName()));
        }

        if(cond.getAgeGoe() != null){
            builder.and(member.age.goe(cond.getAgeGoe()));
        }



        return queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name
                ))
                .from(member)
                .leftJoin(member.team,team)
                .where(builder)
                .fetch();
    }
}
