package com.lambdaschool.bookStore.services;

import com.lambdaschool.bookStore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookStore.models.Role;
import com.lambdaschool.bookStore.models.User;
import com.lambdaschool.bookStore.models.UserRoles;
import com.lambdaschool.bookStore.repository.RoleRepository;
import com.lambdaschool.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository rolerepos;

    @Autowired
    UserRepository userrepos;

    @Override
    public List<Role> findAll()
    {
        List<Role> list = new ArrayList<>();
        rolerepos.findAll()
                 .iterator()
                 .forEachRemaining(list::add);
        return list;
    }


    @Override
    public Role findRoleById(long id)
    {
        return rolerepos.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role findByName(String name)
    {
        Role rr = rolerepos.findByNameIgnoreCase(name);

        if (rr != null)
        {
            return rr;
        } else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        rolerepos.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
        rolerepos.deleteById(id);
    }


    @Transactional
    @Override
    public Role save(Role role)
    {
        Role newRole = new Role();
        newRole.setName(role.getName());

        ArrayList<UserRoles> newUsers = new ArrayList<>();
        for (UserRoles ur : role.getUserroles())
        {
            long id = ur.getUser()
                        .getUserid();
            User user = userrepos.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
            newUsers.add(new UserRoles(ur.getUser(), newRole));
        }
        newRole.setUserroles(newUsers);

        return rolerepos.save(role);
    }
}
