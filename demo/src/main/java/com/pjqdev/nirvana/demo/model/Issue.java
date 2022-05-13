package com.pjqdev.nirvana.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected String node_id;
    protected long number;
    protected String title;
    protected String url;
    protected String repository_url;
    protected String html_url;
    protected String state;
    protected Boolean locked;
    protected Timestamp created_at;
    protected Timestamp updated_at;
    protected Timestamp closed_at;
    protected String body;
    protected String timeline_url;
    protected String username;
    protected String user_url;

    public Issue() {
    }

    public Issue(long id, String node_id, long number, String title, String url, String repository_url, String html_url,
                 String state, Boolean locked, Timestamp created_at, Timestamp updated_at, Timestamp closed_at,
                 String body, String timeline_url, String username, String user_url) {

        this.id = id;
        this.node_id = node_id;
        this.number = number;
        this.title = title;
        this.url = url;
        this.repository_url = repository_url;
        this.html_url = html_url;
        this.state = state;
        this.locked = locked;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.closed_at = closed_at;
        this.body = body;
        this.timeline_url = timeline_url;
        this.username = username;
        this.user_url = user_url;
    }

    public long getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public long getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getRepository_url() {
        return repository_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getState() {
        return state;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp getClosed_at() {
        return closed_at;
    }

    public String getBody() {
        return body;
    }

    public String getTimeline_url() {
        return timeline_url;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_url() {
        return user_url;
    }
}