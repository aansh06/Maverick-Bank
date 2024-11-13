package com.hexaware.MaverickBank.service;

import com.hexaware.MaverickBank.dto.RoleDTO;
import com.hexaware.MaverickBank.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO role);
    RoleDTO getRoleById(Integer roleId);
    List<RoleDTO> getAllRoles();
}