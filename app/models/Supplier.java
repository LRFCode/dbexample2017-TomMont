package models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

public class Supplier
{
    @Id
    @Column(name = "SupplierID")
    private int supplierID;

    @Column(name = "CompanyName")
    private String companyName;

    public int getSupplierID()
    {
        return supplierID;
    }

    public void setSupplierID(int supplierID)
    {
        this.supplierID = supplierID;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
