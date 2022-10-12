package oasip.Repository;

import oasip.Entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryOwnerRepository extends JpaRepository<CategoryOwner, CategoryOwnerId> {

    List<CategoryOwner> findAllByUserIdLecturer_Id(Integer id);


    @Query("select c from CategoryOwner c where c.userIdLecturer = ?1")
    List<CategoryOwner>  findByUserIdLecturer(User user);
}
