package infobip.interview.task.urlshortener.model;

import javax.persistence.*;

@Entity
@Table(name = "Url")
public class Url {

    private Long id;
    private String originalUrl;
    private String shortenedUrlCode;
    private Integer redirectType;
    private Integer numberOfUrlVisits;
    private Account account;

    public Url() {

    }

    public Url(String originalUrl, String shortenedUrlCode, Integer redirectType, Integer numberOfUrlVisits, Account account) {
        this.originalUrl = originalUrl;
        this.shortenedUrlCode = shortenedUrlCode;
        this.redirectType = redirectType;
        this.account = account;
        this.numberOfUrlVisits = numberOfUrlVisits;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "originalUrl", nullable = false)
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }


    @Column(name = "shortenedUrlCode", nullable = false)
    public String getShortenedUrlCode() {
        return shortenedUrlCode;
    }

    public void setShortenedUrlCode(String shortenedUrlCode) {
        this.shortenedUrlCode = shortenedUrlCode;
    }


    @Column(name = "redirectType", nullable = false)
    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }


    @Column(name = "numberOfUrlVisits", nullable = false)
    public Integer getNumberOfUrlVisits() {
        return numberOfUrlVisits;
    }

    public void setNumberOfUrlVisits(Integer numberOfUrlVisits) {
        this.numberOfUrlVisits = numberOfUrlVisits;
    }

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "fk_account_Id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
