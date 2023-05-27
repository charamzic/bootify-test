package cz.javajitsu.my_app.service;

import cz.javajitsu.my_app.domain.Reservation;
import cz.javajitsu.my_app.domain.User;
import cz.javajitsu.my_app.model.UserDTO;
import cz.javajitsu.my_app.repos.ReservationRepository;
import cz.javajitsu.my_app.repos.UserRepository;
import cz.javajitsu.my_app.util.NotFoundException;
import cz.javajitsu.my_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(final UserRepository userRepository,
            final ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map((user) -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public String getReferencedWarning(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Reservation userReservation = reservationRepository.findFirstByUser(user);
        if (userReservation != null) {
            return WebUtils.getMessage("user.reservation.user.referenced", userReservation.getId());
        }
        return null;
    }

}
