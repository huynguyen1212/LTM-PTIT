package ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
   @Query(value = "select friend from FriendEntity friend where (friend.user_id_1.id=?1 and friend.user_id_2.id=?2) or (friend.user_id_2.id=?1 and friend.user_id_1.id=?2)")
   FriendEntity findFirstByUser_id_1AndUser_id_2(Long user_id_1, Long user_id_2);

   @Query(value = "select friend from FriendEntity friend where (friend.user_id_2.id=:userId and friend.user_id_1.username like %:username% and friend.confirmed=true) or (friend.user_id_1.id=:userId and friend.user_id_2.username like %:username% and friend.confirmed=true)")
   List<FriendEntity> findAllByUsernameFriend(Long userId, String username);

   @Query(value = "select friend from FriendEntity friend where friend.user_id_2.id=:userId and friend.user_id_1.username like %:username% and friend.confirmed=false")
   List<FriendEntity> findAllByUsernameFriendRequest(Long userId, String username);
}
