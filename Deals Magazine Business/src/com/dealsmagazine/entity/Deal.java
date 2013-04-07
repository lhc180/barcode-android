/*
 * 
 * © 2011 WhereYouShop.com  All Rights Reserved | http://www.whereyoushop.com 
 * Emergency 24    | The WhereYouShop Team  
 *
 */

package com.dealsmagazine.entity;

/*
 * Deal
 */
public class Deal {
	public String id;
	public String status;
	public String dealtype;
	public String market;
	public String geotargeting;
	public String category;
	public String headline;
	public String emailsubject;
	public String startdate;
	public String enddate;
	public String remainingquantity;
	public String expdate;

	public String getDealId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public String getDealType() {
		return dealtype;
	}

	public String getMarket() {
		return market;
	}

	public String getGeoTargeting() {
		return geotargeting;
	}

	public String getCategory() {
		return category;
	}

	public String getDealHeadLine() {
		return headline;
	}

	public String getEmailSubject() {
		return emailsubject;
	}

	public String getStartDate() {
		return startdate;
	}

	public String getEndDate() {
		return enddate;
	}

	public String getRemining_quantity() {
		return remainingquantity;
	}

	public String getDate_exp() {
		return expdate;
	}

}
