package ptit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    @Query(value = "select user from UserEntity user where user.username like %:username%")
    List<UserEntity> findAllByUsername(String username);
}
