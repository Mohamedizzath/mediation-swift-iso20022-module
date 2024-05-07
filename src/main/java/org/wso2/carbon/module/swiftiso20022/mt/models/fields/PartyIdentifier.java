package org.wso2.carbon.module.swiftiso20022.mt.models.fields;

import java.util.List;

/**
 * Super class of Party Identifier fields.
 * This class only contains all possible attributes with setters, and getters.
 * Parsing logic for child classes should be implemented in the child class.
 */
public class PartyIdentifier {
    private char option;

    // example: /293456-1254349-82
    private String account;

    // example: VISTUS31
    private String identifierCode;

    // example: NIDN/DE/121231234342
    private String partyIdentifier;
    private String location;

    // format: (number)/(name and address)
    // example for 50F: 1/MANN GEORG
    // OR
    // format: (name and address)
    // example for 59: MANN GEORG
    private List<String> details;

    /**
     * Constructor to initialize only the option.
     *
     * @param option single character which identify the option.
     */
    public PartyIdentifier(char option) {
        this.option = option;
    }

    /**
     * Constructor to initialize all attributes except account.
     * Account is present in special cases.
     *
     * @param option single character which identify the option.
     * @param partyIdentifier String specifying account no
     * @param identifierCode String specified in SWIFT party identifier format
     * @param location String with character set x
     * @param details String array in character set x
     */
    public PartyIdentifier(char option, String partyIdentifier,
                           String identifierCode, String location, List<String> details) {
        this.option = option;
        this.partyIdentifier = partyIdentifier;
        this.identifierCode = identifierCode;
        this.location = location;
        this.details = details;
    }

    public char getOption() {
        return option;
    }

    public void setOption(char option) {
        this.option = option;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(String identifierCode) {
        this.identifierCode = identifierCode;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
