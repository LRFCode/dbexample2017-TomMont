package models;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Category
{
    @Id
    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "CategoryName")
    private String categoryName;

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
}
