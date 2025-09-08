package th.mfu.boot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    public UserRepository repo;

    // Register new user
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        repo.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // List all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    // Get a single user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = repo.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user by id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> existing = repo.findById(id);
        if (!existing.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
