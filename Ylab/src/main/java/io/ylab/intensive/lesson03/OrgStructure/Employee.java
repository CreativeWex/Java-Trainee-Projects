package io.ylab.intensive.lesson03.OrgStructure;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private Long id;
    private Long bossId;
    private String name;
    private String position;
    private Employee boss;
    private List<Employee> subordinate = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getBossId() {
        return bossId;
    }
    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Employee getBoss() {
        return boss;
    }
    public void setBoss(Employee boss) {
        this.boss = boss;
    }
    public List<Employee> getSubordinate() {
        return subordinate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", bossId=" + bossId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", boss=" + boss;
    }
}
