/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author phamthainb
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	private Date created_at;

	@LastModifiedDate
	private Date updated_at;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "username")
	private String username;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "last_online")
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_online;

	@Column(name = "online")
	private Boolean online;

	public UserEntity(String username, String password1) {
		this.username = username;
		this.password = password1;
	}

	@Override
	public String toString() {
		return "UserEntity{" +
				"id=" + id +
				", created_at=" + created_at +
				", updated_at=" + updated_at +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", username='" + username + '\'' +
				", avatar='" + avatar + '\'' +
				", last_online=" + last_online +
				", online=" + online +
				'}';
	}
}
