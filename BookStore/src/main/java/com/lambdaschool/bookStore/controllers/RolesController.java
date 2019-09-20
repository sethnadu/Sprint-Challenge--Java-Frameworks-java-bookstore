package com.lambdaschool.bookStore.controllers;

import com.lambdaschool.bookStore.models.Book;
import com.lambdaschool.bookStore.models.ErrorDetail;
import com.lambdaschool.bookStore.models.Role;
import com.lambdaschool.bookStore.services.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);
    @Autowired
    RoleService roleService;

    //    GET localhost:2019/roles/roles
    @ApiOperation(value = "Return All Roles", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All Roles Returned", response = Role.class),
            @ApiResponse(code = 404, message = "Error returning Roles", response = ErrorDetail.class)})
    @GetMapping(value = "/roles",
                produces = {"application/json"})
    public ResponseEntity<?> listRoles(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    //    GET localhost:2019/roles/role/{roleid}
    @ApiOperation(value = "Return Role by ID", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Role Return With ID", response = Role.class),
            @ApiResponse(code = 404, message = "Error returning Role with ID", response = ErrorDetail.class)})
    @GetMapping(value = "/role/{roleId}",
                produces = {"application/json"})
    public ResponseEntity<?> getRoleById(HttpServletRequest request,
                                         @PathVariable
                                                 Long roleId)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Role r = roleService.findRoleById(roleId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //    GET localhost:2019/roles/role/name/{roleName}
    @ApiOperation(value = "Return Roles by Name", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Role Returned By Name", response = Role.class),
            @ApiResponse(code = 404, message = "Error returning Role By Name", response = ErrorDetail.class)})
    @GetMapping(value = "/role/name/{roleName}",
                produces = {"application/json"})
    public ResponseEntity<?> getRoleByName(HttpServletRequest request,
                                           @PathVariable
                                                   String roleName)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Role r = roleService.findByName(roleName);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //    POST localhost:2019/roles/role/
    @ApiOperation(value = "Add Role", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Role Added", response = void.class),
            @ApiResponse(code = 500, message = "Error adding Role", response = ErrorDetail.class)})
    @PostMapping(value = "/role")
    public ResponseEntity<?> addNewRole(HttpServletRequest request, @Valid
    @RequestBody
            Role newRole) throws URISyntaxException
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        newRole = roleService.save(newRole);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{roleid}")
                                                    .buildAndExpand(newRole.getRoleid())
                                                    .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //    DELETE localhost:2019/roles/role/{roleid}
    @ApiOperation(value = "Delete by Role Id", response = Role.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Role Deleted By Id", response = void.class),
            @ApiResponse(code = 500, message = "Role Not Deleted By Id", response = ErrorDetail.class)})
    @DeleteMapping("/role/{id}")
    public ResponseEntity<?> deleteRoleById(HttpServletRequest request,
                                            @PathVariable
                                                    long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
