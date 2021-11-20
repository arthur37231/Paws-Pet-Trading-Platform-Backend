package usyd.elec5619.group42.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
