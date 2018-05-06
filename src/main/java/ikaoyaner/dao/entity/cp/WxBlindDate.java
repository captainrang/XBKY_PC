package ikaoyaner.dao.entity.cp;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class WxBlindDate implements Serializable{
	
	private static final long serialVersionUID = 8772314794558133573L;
	/**
	 * id 在表中是自增长 非必传参数
	 */
	private Long id; 
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别 0-男 1-女
	 */
	private String sex;
	/**
	 * 出生日期
	 */
	private String birthdate;
	/**
	 * 微信号
	 */
	private String wxusername;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * email
	 */
	private String  email;
	/**
	 * 居住城市
	 */
	private String  liveCity;
	/**
	 * 毕业学校
	 */
	private String  graduateSchool;
	/**
	 * 身份证明
	 */
	private String  identity;
	/**
	 * 个性化照片
	 */
	private String  personphoto;
	
	/**
	 * 匹配好友   0- 为我匹配异性好友 1-为我匹配同性好友 默认值 0 
	 */
	private int matchfriend;
	
	/**
	 * 聊天交友  0 邮箱书信交友 1 微信聊天交友 默认值 0
	 */
	private int chatfriend;
	
	/**
	 * 匹配地区  0 优先匹配同学校/同地区的好友 1 匹配同学校 2匹配同地区 默认值0
	 */
	private int matcharea;

	/**
	 * 现在的学校
	 */
	private String nowSchool;
	
	private String openid;

	@Transient
	private String code;

	/**
	 * 创建时间
	 * @return
	 */
	private Date createTime;

    /**
     * 最小年龄
     */
	private int minAge;

    /**
     * 最大年龄
     */
	private int maxAge;

	/**
	 * 年龄
	 */
	private int age;

    /**
     * 最大身高
     */
	private String maxHeight;

    /**
     * 最小身高
     */
	private String minHeight;
	/**
	 * 身高
	 */
	private String height;

	@Transient
	private String validateCode;
    /**
     * 匹配对象城市
     */
	private String matchcity;

	/**
	 * qq
	 */
	private String qq;

	/**
	 * 是否研究生毕业 默认0 非研究生
	 */
	private int isPostgraduate;
	/**
	 * 匹配研究生 0 匹配研究生 1 匹配任何人
	 */
	private int matchPostgraduate;

	/**
	 * 匹配年龄 0 匹配两岁差距 1 匹配5岁差距 2 其他
	 */
	private int matchAge;

	private String liveProvincial;

	private String liveUrban;

	private String liveArea;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getWxusername() {
		return wxusername;
	}
	public void setWxusername(String wxusername) {
		this.wxusername = wxusername;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLiveCity() {
		return liveCity;
	}
	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}
	public String getGraduateSchool() {
		return graduateSchool;
	}
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPersonphoto() {
		return personphoto;
	}
	public void setPersonphoto(String personphoto) {
		this.personphoto = personphoto;
	}
	public int getMatchfriend() {
		return matchfriend;
	}
	public void setMatchfriend(int matchfriend) {
		this.matchfriend = matchfriend;
	}
	public int getChatfriend() {
		return chatfriend;
	}
	public void setChatfriend(int chatfriend) {
		this.chatfriend = chatfriend;
	}
	public int getMatcharea() {
		return matcharea;
	}
	public void setMatcharea(int matcharea) {
		this.matcharea = matcharea;
	}


	public String getNowSchool() {
		return nowSchool;
	}

	public void setNowSchool(String nowSchool) {
		this.nowSchool = nowSchool;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMatchPostgraduate() {
		return matchPostgraduate;
	}

	public void setMatchPostgraduate(int matchPostgraduate) {
		this.matchPostgraduate = matchPostgraduate;
	}

	public int getMatchAge() {
		return matchAge;
	}

	public void setMatchAge(int matchAge) {
		this.matchAge = matchAge;
	}

	public String getLiveProvincial() {
		return liveProvincial;
	}

	public void setLiveProvincial(String liveProvincial) {
		this.liveProvincial = liveProvincial;
	}

	public String getLiveUrban() {
		return liveUrban;
	}

	public void setLiveUrban(String liveUrban) {
		this.liveUrban = liveUrban;
	}

	public String getLiveArea() {
		return liveArea;
	}

	public void setLiveArea(String liveArea) {
		this.liveArea = liveArea;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getIsPostgraduate() {
		return isPostgraduate;
	}

	public void setIsPostgraduate(int isPostgraduate) {
		this.isPostgraduate = isPostgraduate;
	}

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getMatchcity() {
        return matchcity;
    }

    public void setMatchcity(String matchcity) {
        this.matchcity = matchcity;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
