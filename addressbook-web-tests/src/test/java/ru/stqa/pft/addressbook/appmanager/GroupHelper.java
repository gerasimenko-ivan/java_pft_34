package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gis on 02.11.2016.
 */
public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    //////////////////////////// single action methods //////////////////////////

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroup(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    private void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public boolean doesExist() {
        return isElementPresent(By.name("selected[]"));
    }

    public boolean doesExist(String groupName) {
        return isElementPresent(By.xpath("//input[@title='Select (" + groupName + ")']"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<GroupData> getGroupList() {
        List<GroupData> groups = new ArrayList<GroupData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData(id, name, null, null);
            groups.add(group);
        }
        return groups;
    }

    //////////////////////////// complex methods //////////////////////////

    public void create(GroupData group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupPage();
    }

    public void modifyGroup(int index, GroupData newGroup) {
        selectGroup(index);
        initGroupModification();
        fillGroupForm(newGroup);
        submitGroupModification();
        returnToGroupPage();
    }

    public void deleteGroup(int index) {
        selectGroup(index);
        deleteSelectedGroups();
        returnToGroupPage();
    }
}
