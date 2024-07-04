// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.exceptions;

public enum ErrorCode {

	E("Error Code"), // just for swagger documentation
	E45("user or tour not found"), E401("access token is experied"), E47("agency or tour not found"),
	E46("costumer or user not found"), A001("Agence not found"), A0021("Client not found"), A031("Depot not found"),
	A044("user not found"), A045("field is empty"), A002("Client not found"), A004(" not found"),
	A0040("Company not found"), A502("EMAIL  EST DEJA AXESITE"), A503("Phone  EST DEJA AXESITE"),
	A504("fax  EST DEJA AXESITE"), A505("family parent not existe"), A506("CATEGORY PARENT NOT EXISTE"),
	A507("the request should not be null"), A508("no category asigned to this company"), A509("email not found"),
	A510("no email asgned to this costumer"), A511("This agency does not have any sectors"), A512("familyCustomer not found"),
	A513("family parent not found"), A514("fax not found"), A515("phone not found"), A516("Costumer not found"),
	A517(" Costumers not found in this sector"), A518("MISSION not found"), A519("SECTEUR not found"),
	S001("suppliers not "), S003("suppliers not found in this company"), B001("brand not found"),

	B003("brands not found in this company"), D001("device not found"), D003("devices not found in this company"),
	Se001("secteur not found"), PgeSize001("Pagination or size can't be 0"), C001("costumer not found"),
	F002("FamilyCostumer not found"),

	A0062("Company Request data cannot be null"), A0063("Company Update Request data cannot be null"),
	A0064("Company Uuid cannot be null"), A0061("Company Not Found"),
	A205("Vous avez d\u00e9pass\u00e9 la taille maximale permise"), A003("no agence asigned to this company"),
	A005("athribut can not be null"), A006("no agence found"), A007("produit not found"), A008("Categorie not found"),
	A010("no company found"), A020(" password incorrect"), A333("user info can not be null"),
	A009("no product found for ths categorie"), A209("Une erreur syst\u00e8me s'est produite"),
	A500("Une erreur syst\u00e8me s'est produite"), A0066("OTP Not Found"), A0067("OTP is not valid"),
	A0014("Email Uuid cannot be null"), A0011("Email Not Found"),

	A0024("Telephone Uuid cannot be null"), A0028("Telephone Not Found"),

	A0022("Phone Not Found"),

	A0401("Login or password incorrect"), D0001("Demande Not Found"),

	A0070("Token Request data cannot be null"), A0068("TOKEN Not Found"),

	A0020("The confirmation password not equal a new password"),
	AS02("Number of locations does not match the sector's rang"),

	P0064("Profile Uuid cannot be null"), P0061("Profile Not Found"), E444("Error sur keycloak"),
	A0403("Token expired"),

	AS064("Agency Uuid cannot be null or contains only spaces"),
	AC099("Costumer Uuid cannot be null or contains only spaces"),

	P001Cs("this costumer doesn't have any emails"),
	P0002("Email Not found for this costumer"),
	E0010("Email Not Found for this costumer"),
	AS0000("No address found for the costumer"),
	
	F000("family uuid cannot be null or contains only spaces"), AS065("Agency Not Found"),

	P001B("this agency doesn't have any phones"), P001C("this costumer doesn't have any phones"),
	B002("brand Uuid cannot be null or contains only spaces"), B006("Brand Uuid cannot be null"),
	A520("Company Uuid cannot be null"), AD001("Address Not found"), P002("Phone Not found"),
	P001A("Phone Not found for that agency"), P0099("There is already Main"), E001("Email Not Found"),
	P001("Number of autorized phones exceed"), E002("Number of autorized emails exceed"), PR00("Only One Main"),
	NPR0("Must Be One Main"), F511("This table don't have any parent or child customer"),
	F512("This Company does not have any sectors"), F513("This Parent does not have any childs"),
	F0064("Parent Uuid cannot be null"), F0067("Family Uuid cannot be null"), F0065("Parent Not Found"),
	AS001B("No address found for the agency"), A0041("Company uuid is null");

	private String code = "";

	@Override
	public String toString() {
		return code;
	}

	private ErrorCode(String code) {
		this.code = code;
	}

}
