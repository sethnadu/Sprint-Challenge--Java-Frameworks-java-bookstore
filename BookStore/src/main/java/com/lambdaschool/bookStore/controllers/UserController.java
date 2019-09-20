package com.lambdaschool.bookStore.controllers;

import com.lambdaschool.bookStore.models.Book;
import com.lambdaschool.bookStore.models.ErrorDetail;
import com.lambdaschool.bookStore.models.Role;
import com.lambdaschool.bookStore.models.User;
import com.lambdaschool.bookStore.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //    GET localhost:2019/users/users
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "List All Users", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All Users Returned", response = User.class),
            @ApiResponse(code = 404, message = "Error returning all Users", response = ErrorDetail.class)})
    @GetMapping(value = "/users",
                produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    //    GET localhost:2019/users/user/{userId}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "List User by ID", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Users Returned By ID", response = User.class),
            @ApiResponse(code = 404, message = "Error returning User By ID", response = ErrorDetail.class)})
    @GetMapping(value = "/user/{userId}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserById(HttpServletRequest request,
                                         @PathVariable
                                                 Long userId)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    //    GET localhost:2019/users/user/name/{userName}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "List User By Name", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User By Name Returned", response = User.class),
            @ApiResponse(code = 404, message = "Error returning User By Name", response = ErrorDetail.class)})
    @GetMapping(value = "/user/name/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserByName(HttpServletRequest request,
                                           @PathVariable
                                                   String userName)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findByName(userName);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    //    GET localhost:2019/users/getusername
    @ApiOperation(value = "Get Current User Info", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Current User Info Returned", response = User.class),
            @ApiResponse(code = 404, message = "Error returning Current User Info", response = ErrorDetail.class)})
    @GetMapping(value = "/getusername",
                produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authentication)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }

    //    POST localhost:2019/users/user/
    @ApiOperation(value = "Add User", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User Added", response = void.class),
            @ApiResponse(code = 500, message = "Error adding User", response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid
    @RequestBody
            User newuser) throws URISyntaxException
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{userid}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //    PUT localhost:2019/users/user/{id}
    @ApiOperation(value = "Update User by Id", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User Updated", response = void.class),
            @ApiResponse(code = 500, message = "User Not Updated", response = ErrorDetail.class)})
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser,
                                        @PathVariable
                                                long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.update(updateUser, id, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    DELETE localhost:2019/users/user/{id}
    @ApiOperation(value = "Delete by User Id", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User Deleted By Id", response = void.class),
            @ApiResponse(code = 500, message = "User Not Deleted By Id", response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request,
                                            @PathVariable
                                                    long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    DELETE localhost:2019/users/user/{userid}/role/{roleid}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Delete Role ID of User Id", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User Role Deleted By Id", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Role from User", response = ErrorDetail.class)})
    @DeleteMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(HttpServletRequest request,
                                                 @PathVariable
                                                         long userid,
                                                 @PathVariable
                                                         long roleid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.deleteUserRole(userid, roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    POST localhost:2019/users/user/{userid}/role/{roleid}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Add Role to User", response = User.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Role Added To User", response = void.class),
            @ApiResponse(code = 500, message = "Error adding Role to User", response = ErrorDetail.class)})
    @PostMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(HttpServletRequest request,
                                               @PathVariable
                                                       long userid,
                                               @PathVariable
                                                       long roleid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.addUserRole(userid, roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}