package com.test9.irc.engine;

public class User {

	private String nick;
	private String realName;
	private boolean away;
	private boolean invisible;
	private boolean wallops;
	private boolean restricted;
	private boolean operator;
	private boolean localOperator;
	private boolean noticeReceipt;
	private static int userID = 0;

	User(String nick, boolean yourself) {
		char prefix = nick.charAt(0);

		if((prefix >= 'A' && prefix <= 'Z') || (prefix >= 'a' && prefix <= 'z'))
			this.nick = nick;
		else
			this.nick = nick.substring(1, nick.length());

		userID++;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userID
	 */
	public static int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public static void setUserID(int userID) {
		User.userID = userID;
	}

	/**
	 * @return the away
	 */
	public boolean isAway() {
		return away;
	}

	/**
	 * @param away the away to set
	 */
	public void setAway(boolean away) {
		this.away = away;
	}

	/**
	 * @return the invisible
	 */
	public boolean isInvisible() {
		return invisible;
	}

	/**
	 * @param invisible the invisible to set
	 */
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/**
	 * @return the wallops
	 */
	public boolean isWallops() {
		return wallops;
	}

	/**
	 * @param wallops the wallops to set
	 */
	public void setWallops(boolean wallops) {
		this.wallops = wallops;
	}

	/**
	 * @return the restricted
	 */
	public boolean isRestricted() {
		return restricted;
	}

	/**
	 * @param restricted the restricted to set
	 */
	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	/**
	 * @return the operator
	 */
	public boolean isOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(boolean operator) {
		this.operator = operator;
	}

	/**
	 * @return the localOperator
	 */
	public boolean isLocalOperator() {
		return localOperator;
	}

	/**
	 * @param localOperator the localOperator to set
	 */
	public void setLocalOperator(boolean localOperator) {
		this.localOperator = localOperator;
	}

	/**
	 * @return the noticeReceipt
	 */
	public boolean isNoticeReceipt() {
		return noticeReceipt;
	}

	/**
	 * @param noticeReceipt the noticeReceipt to set
	 */
	public void setNoticeReceipt(boolean noticeReceipt) {
		this.noticeReceipt = noticeReceipt;
	}
}
