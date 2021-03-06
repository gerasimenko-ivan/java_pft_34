package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        groupsCache = null;
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
        groupsCache = null;
    }

    public void selectGroup(int index) {
        findElements(By.name("selected[]")).get(index).click();
    }

    private void selectGroupById(int id) {
        find(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
        groupsCache = null;
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
        return findElements(By.name("selected[]")).size();
    }

    private List<GroupData> groupsCache = null;

    public List<GroupData> list() {
        if (groupsCache != null) {
            return new ArrayList<GroupData>(groupsCache);
        } else {
            groupsCache = new ArrayList<GroupData>();
            List<WebElement> elements = findElements(By.cssSelector("span.group"));
            for (WebElement element : elements) {
                String name = element.getText();
                int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                GroupData group = new GroupData().withId(id).withName(name);
                groupsCache.add(group);
            }
            return groupsCache;
        }

    }

    public Groups all() {
        return new Groups(list());
    }

    //////////////////////////// complex methods //////////////////////////

    public void create(GroupData group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupPage();
    }

    public void modify(int index, GroupData newGroup) {
        selectGroup(index);
        initGroupModification();
        fillGroupForm(newGroup);
        submitGroupModification();
        returnToGroupPage();
    }

    public void modifyById(int modifiedGroupId, GroupData newGroup) {
        selectGroupById(modifiedGroupId);
        initGroupModification();
        fillGroupForm(newGroup);
        submitGroupModification();
        returnToGroupPage();
    }

    public void delete(int index) {
        selectGroup(index);
        deleteSelectedGroups();
        returnToGroupPage();
    }

    public void deleteById(int id) {
        selectGroupById(id);
        deleteSelectedGroups();
        returnToGroupPage();
    }
}
