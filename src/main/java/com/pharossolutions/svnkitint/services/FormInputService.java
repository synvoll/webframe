package com.pharossolutions.svnkitint.services;

import com.pharossolutions.svnkitint.models.FormInput;
import com.pharossolutions.svnkitint.models.SvnUser;

public interface FormInputService {

	/**
	 * Transforms form inputs filled by the user to text and commits the text in
	 * a new/existing file defined in formInput.packetName
	 * 
	 * @param formInput
	 *            contains form inputs filled by the user
	 * @param user
	 *            current logged in user, user used in SVN authentication
	 * @return
	 */
	boolean commit(FormInput formInput, SvnUser user);

}
