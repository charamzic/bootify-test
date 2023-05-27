package cz.javajitsu.my_app.repos;

import cz.javajitsu.my_app.domain.Reservation;
import cz.javajitsu.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findFirstByUser(User user);

}
