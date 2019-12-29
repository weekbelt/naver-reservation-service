package me.weekbelt.naverreservation.domain.display;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DisplayInfoImageRepository extends JpaRepository<DisplayInfoImage, Long> {

    @Query("select dim from DisplayInfoImage dim" +
            " join fetch dim.fileInfo fi" +
            " join fetch dim.displayInfo di" +
            " where di.id = :displayInfoId")
    DisplayInfoImage findDisplayInfoImageByDisplayInfoId(@Param("displayInfoId") Long displayInfoId);
}