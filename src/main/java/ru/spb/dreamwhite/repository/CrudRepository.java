package ru.spb.dreamwhite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.dreamwhite.model.User;

@Repository
@Transactional ( readOnly = true )
public interface CrudRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query ("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param ("id") int id);

    User getByEmail(String email);

}
