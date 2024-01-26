package az.matrix.aptek.ms.repository;

import az.matrix.aptek.ms.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine,Long>, JpaSpecificationExecutor<Medicine> {
}
