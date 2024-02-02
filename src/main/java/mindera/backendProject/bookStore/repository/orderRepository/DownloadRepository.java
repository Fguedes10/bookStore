package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {


    @Query("SELECT j FROM Download j WHERE j.orderModel.id = :orderModelId")
    List<Download> findDownloadByOrder(@Param("orderModelId") Long orderModelId);
}
