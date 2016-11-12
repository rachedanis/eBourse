package com.bfigroupe.ebourse.security;

public interface ISecurityUserService {

	String validatePasswordResetToken(long id, String token);

}