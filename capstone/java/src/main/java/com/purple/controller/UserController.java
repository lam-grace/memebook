package com.purple.controller;


import com.purple.dao.MemeDaoJdbc;
import com.purple.dao.UserDao;
import com.purple.handler.MemeHandler;
import com.purple.handler.MemeHandlerable;
import com.purple.handler.UserHandlerable;
import com.purple.model.*;
import com.purple.security.AuthAspect;
import com.purple.security.Authorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {

    private final UserHandlerable userHandler;
    private MemeHandlerable memeHandler;

    @Autowired
    public UserController(UserHandlerable userHandler, MemeHandlerable memeHandler) {

        this.userHandler = userHandler;
        this.memeHandler = memeHandler;
    }

    /*
        JSON example
        {
        "userName":"TestUsername",
        "password":"testpassword"
        }
     */
    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpServletResponse response) {
        if (user == null) {
            return ResponseEntity.badRequest().body("username or password is incorrect");
        }
        User returnUser = this.userHandler.login(user.getUsername(), user.getPassword());
        if (returnUser == null) {
            return ResponseEntity.badRequest().body("username or password is incorrect");
        }
        AuthAspect.setUserCookie(returnUser,response);
        return ResponseEntity.ok(returnUser);
    }

    @GetMapping(value = "/user/current",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentOrBlankUser(HttpServletRequest request) {
        User currentUser = super.getCurrentUserFromCookie(request);
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        }
        return ResponseEntity.ok(new User());
    }

    @GetMapping(value = "/user/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(this.userHandler.getAllUsers());
    }

    @PutMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<?> updateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("User is empty");
        }
        User currentUser = super.getCurrentUserFromAttribute(request);

        User newlyAddedUser = this.userHandler.saveUser(currentUser,user,super.getErrorMessages());
        if (newlyAddedUser != null) {
            if (newlyAddedUser.getUserId() == currentUser.getUserId()){
                AuthAspect.setUserCookie(newlyAddedUser, response);
            }
            return ResponseEntity.ok().body(newlyAddedUser);
        }
        return ResponseEntity.badRequest().body(super.getErrorString());
    }


    @PostMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        try{
            User newlyAddedUser = this.userHandler.registerUser(null,user,super.getErrorMessages());
            if (newlyAddedUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newlyAddedUser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ErrorResponse errors = new ErrorResponse(super.getErrorString());

        return ResponseEntity.badRequest().body(errors);
    }



    @GetMapping(value = "/user/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<User> getUser(HttpServletRequest request,HttpServletResponse response,@PathVariable long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        User searchedUser = this.userHandler.getUserById(currentUser,id);
        if (searchedUser != null) {
            return ResponseEntity.ok(searchedUser);
        }

        return ResponseEntity.status(403).build();
    }

    @GetMapping(value = "/user/role/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<User> getRole(HttpServletRequest request,HttpServletResponse response,@PathVariable long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        User searchedUser = this.userHandler.getRole(id);
        if (searchedUser != null) {
            return ResponseEntity.ok(searchedUser);
        }

        return ResponseEntity.status(403).build();
    }



    @PostMapping(value = "/user/downgrade/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized(requiredRole = "Admin")
    public ResponseEntity<User> downgradeAdminToUser(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        User searchedUsers = this.userHandler.downgradeAdminToUser(id);
        if (searchedUsers != null) {
            return ResponseEntity.ok(searchedUsers);
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/user/upgrade/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized(requiredRole = "Admin")
    public ResponseEntity<User> upgradeUserToAdmin(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        User searchedUsers = this.userHandler.upgradeUserToAdmin(id);
        if (searchedUsers != null) {
            return ResponseEntity.ok(searchedUsers);
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping(value = "/user/meme/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<?> getUserMemes(HttpServletRequest request,HttpServletResponse response,@PathVariable long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        List<Meme> usersMemes = this.memeHandler.filterMemesByAuthor(id);
        if (usersMemes != null) {
            return ResponseEntity.ok(usersMemes);
        }

        return ResponseEntity.status(403).build();
    }

    //view favourite memes (by user)
    @GetMapping(value = "/user/meme/favourites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterMemesByFavourites(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.filterMemesByFavourites(id));
    }

    //returns a boolean saying if this user liked this meme
    @GetMapping(value = "/user/meme/checkFavourite/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkFavouriteMeme(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.userHandler.checkMemeIsFavourite(id, user));
    }

    @PostMapping(value = "/userSearch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized(requiredRole = "Admin")
    public ResponseEntity<?> getUsers(HttpServletRequest request, HttpServletResponse response, @RequestBody UserSearchCriteria search) {
        User currentUser = super.getCurrentUserFromAttribute(request);

        List<User> searchedUsers = this.userHandler.getUsers(currentUser,search);
        if (searchedUsers != null) {
            return ResponseEntity.ok(searchedUsers);
        }
        return ResponseEntity.badRequest().body(super.getErrorString());
    }

    @GetMapping(value = "/userLogout",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<User> logoutUser(HttpServletRequest request,HttpServletResponse response) {
        AuthAspect.removeUserFromCookie(request,response);
        return ResponseEntity.ok(new User());
    }

    @Authorized
    @PostMapping(value = "/user/delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User currentUser = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.userHandler.deleteUser(currentUser, id));
    }




}