/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import rookiesspring.dto.RemoveUserDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.UserRequestDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserPaginationShort;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.mapper.UserMapper;
import rookiesspring.model.Address;
import rookiesspring.model.Role;
import rookiesspring.model.User;
import rookiesspring.repository.UserRepository;

/**
 *
 * @author HP
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;

    User user;
    UserResponseDTOShort responseShort;
    UserPaginationShort paginationShort;
    UserDTO userDTO;
    UserUpdateDTO updateDTO;
    UserResponseDTO responseDTO;
    UserRequestDTO requestDTO;
    Address address;
    RemoveUserDTO removeDTO;

    public UserServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        user = new User(1);
        user.setEmail("test@gmail.com");
        user.setUsername("User 1");

        address = new Address();
        responseDTO = new UserResponseDTO(1, "User 1", "test@gmail.com", true, 20, "0123456879", List.of(), address, Set.of(Role.USER.toString()));
        responseShort = new UserResponseDTOShort(1, "User 1", "test@gmail.com", false, false, Set.of(Role.USER.toString()));
        paginationShort = new UserPaginationShort(List.of(responseShort), 1);
        userDTO = new UserDTO("User 1", "test@gamil.com", "1", "Test", "Test", true, 20, "0123456789", "1", null, null, null, null, null, "City 1");
        updateDTO = new UserUpdateDTO(1, "User 1", "test@gamil.com", "Test", "Test", true, 20, "0123456789", "1", null, null, null, null, null, "City 1");
        requestDTO = new UserRequestDTO();
        removeDTO = new RemoveUserDTO(1, false);
    }

    @Test
    public void testFindById_shouldReturnUserResponse() {
        when(repository.findProjectedById(Mockito.any(Long.class))).thenReturn(Optional.of(responseShort));

        UserResponseDTOShort result = service.findById(1l);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(user.getId());
    }

    @Test
    public void testFindById_shouldThrowException() {
        when(repository.findProjectedById(Mockito.any(Long.class))).thenThrow(EntityNotFoundException.class);
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1l);
        });
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testFindAllUser() {

        when(repository.findAllProjectedBy(Mockito.any(PageRequest.class))).thenReturn(List.of(responseShort));
        when(repository.count()).thenReturn(1l);

        UserPaginationShort result = service.findAllUser(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getCount()).isEqualTo(1l);
        assertThat(result.getUsers().get(0)).isEqualTo(responseShort);

    }

    @Test
    public void testGetUserInfo_shoutldReturnUserResponseDTO() {
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(user));
        when(mapper.ToResponseDTO(Mockito.any(User.class))).thenReturn(responseDTO);

        UserResponseDTO result = service.getUserInfo(Mockito.anyLong());
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    public void testUpdateOne_shouldReturnUser() {
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(user));
        when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        doNothing().when(mapper).toUpdateUserFromDTO(updateDTO, user);
        when(repository.save(Mockito.any(User.class))).thenReturn(user);
        when(mapper.ToResponseDTOShort(Mockito.any(User.class))).thenReturn(responseShort);
        UserResponseDTOShort result = service.updateOne(updateDTO);
        assertThat(result).isNotNull();
    }

    @Test
    public void testUpdateOne_shouldThrowEntityExistsException() {
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(user));
        when(repository.existsByEmail(Mockito.anyString())).thenReturn(true).thenThrow(EntityExistsException.class);
        Exception exception = Assertions.assertThrows(EntityExistsException.class, () -> {
            service.updateOne(updateDTO);
        });
        assertThat(exception.getMessage()).isEqualTo("Email has already Existed");
    }

    @Test
    public void testDeleteById_shouldReturnVoid() {
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(Mockito.anyLong());
        doNothing().when(repository).softDeleteById(Mockito.anyLong());
        RemoveUserDTO remove_hard = new RemoveUserDTO(1, true);
        Assertions.assertAll(() -> {
            service.deleteById(remove_hard);
        });
        RemoveUserDTO remove_soft = new RemoveUserDTO(1, false);
        Assertions.assertAll(() -> {
            service.deleteById(remove_soft);
        });
    }

    @Test
    public void testBlockOrUnblockUser_shouldReturnUser() {
        User nonblockUser = new User(1); // Should be lock after test
        nonblockUser.setBlock(false);

        User blockedUser = new User(2); // Should be un-lock after test
        blockedUser.setBlock(true);

        when(repository.findById(1l)).thenReturn(Optional.ofNullable(nonblockUser));
        when(repository.findById(2l)).thenReturn(Optional.ofNullable(blockedUser));

        // When block
        when(repository.save(nonblockUser)).thenReturn(nonblockUser).thenAnswer((invocation) -> {
            var u = (User) invocation.getArgument(0);
            assertThat(u.isBlock()).isTrue();
            return nonblockUser;
        });

        //When unblock
        when(repository.save(blockedUser)).thenReturn(blockedUser).thenAnswer((invocation) -> {
            var u = (User) invocation.getArgument(0);
            assertThat(u.isBlock()).isFalse();
            return blockedUser;
        });
        
        // This would invalidate changes
        when(mapper.ToResponseDTOShort(Mockito.any(User.class))).thenReturn(responseShort);

        UserResponseDTOShort block_user = service.blockOrUnblockUser(1, true);
        UserResponseDTOShort unblock_user = service.blockOrUnblockUser(2, false);
    }

    @Test
    public void testRestoreUser_shouldReturnNothing() {
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        doNothing().when(repository).restoreUser(Mockito.anyLong());
        Assertions.assertAll(() -> {service.restoreUser(1l);});
    }
    @Test
    public void testRestoreUser_shouldThrowException() {
        when(repository.existsById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.restoreUser(1l);
        });
        assertThat(exception.getMessage()).isNull();
    }

}
