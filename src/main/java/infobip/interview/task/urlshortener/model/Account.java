package infobip.interview.task.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {

    private String accountId;
    private String password;
    private List<Url> urls;

    public Account(){

    }

    public Account(String accountId) {
        this.accountId = accountId;
    }

    public Account(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "accountId", nullable = false, unique = true)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    @Column (name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @OneToMany(targetEntity=Url.class, mappedBy = "account", cascade = CascadeType.ALL)
    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> Urls) {
        this.urls = Urls;
    }
}
