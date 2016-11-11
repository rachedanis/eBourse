package com.bfigroupe.ebourse.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.security.ActiveUserStore;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.service.IUserService;
import com.bfigroupe.ebourse.web.dto.UserRoleDto;
import com.bfigroupe.ebourse.web.error.UserNotFoundException;
import com.bfigroupe.ebourse.web.util.GenericResponse;

@Controller
public class UsersController {

	@Autowired
	ActiveUserStore activeUserStore;

	@Autowired
	private MessageSource messages;

	@Autowired
	private IUserService userService;

	@Autowired
	private IDAOService daoService;

	private List<User> users;

	private List<String> loggedUsers;

	// GET Users
	@PreAuthorize("hasAuthority('USERS_UPDATE_PRIVILEGE')")
	@RequestMapping(value = "/users")
	public String getUsers(final Model model, HttpServletRequest request) {

		users = userService.getAll();
		loggedUsers = activeUserStore.getUsers();
		model.addAttribute("Users", users);
		model.addAttribute("loggedUsers", loggedUsers);
		return "users";
	}

	// API

	// Remove a User
	@RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('USERS_UPDATE_PRIVILEGE')")
	@ResponseBody
	public GenericResponse removeUser(final Locale locale, @PathVariable final String id) {
		final User user = userService.findById(Long.parseLong(id));
		if (user == null)
			throw new UserNotFoundException();
		daoService.forcedDeleteUser(user);
		return new GenericResponse(messages.getMessage("message.user.delete.success", null, locale), "empty");
	}

	// Remove a User
	@RequestMapping(value = "/users/setRole", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('USERS_UPDATE_PRIVILEGE')")
	@ResponseBody
	public GenericResponse setRole(final Locale locale, @Valid UserRoleDto userRoleDto) {
		daoService.setUserRole(userRoleDto);
		return new GenericResponse(messages.getMessage("message.user.updateRole.success", null, locale), "empty");
	}

	// Enable or Disable a User
	@RequestMapping(value = "/users/enable-disable/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('USERS_UPDATE_PRIVILEGE')")
	@ResponseBody
	public GenericResponse enableOrDisableUser(final Model model, final Locale locale, @PathVariable final String id) {
		User user = userService.findById(Long.parseLong(id));
		user.setEnabled(!user.isEnabled());
		userService.saveRegisteredUser(user);
		if (user.isEnabled())
			return new GenericResponse(messages.getMessage("message.user.enable.success", null, locale), "empty");
		return new GenericResponse(messages.getMessage("message.user.disable.success", null, locale), "empty");
	}
}
