package cl.talento.otec.admin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.CertificacionReuf;

@Repository
public interface CertificacionReufRepository extends JpaRepository<CertificacionReuf, Integer> {
}
