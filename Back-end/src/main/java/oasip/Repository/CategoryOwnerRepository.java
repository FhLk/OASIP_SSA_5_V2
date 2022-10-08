package oasip.Repository;

import oasip.Entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryOwnerRepository extends JpaRepository<CategoryOwner, CategoryOwnerId> {

    CategoryOwner findAllByUserIdLecturer_Email(String email);
}
