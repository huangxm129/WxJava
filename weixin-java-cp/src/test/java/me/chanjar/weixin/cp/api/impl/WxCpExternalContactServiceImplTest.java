package me.chanjar.weixin.cp.api.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.ApiTestModule;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertNotNull;

@Guice(modules = ApiTestModule.class)
public class WxCpExternalContactServiceImplTest {
  @Inject
  private WxCpService wxCpService;
  @Inject
  protected ApiTestModule.WxXmlCpInMemoryConfigStorage configStorage;
  private String userId = "someone" + System.currentTimeMillis();

  @Test
  public void testGetExternalContact() throws WxErrorException {
    String externalUserId = this.configStorage.getExternalUserId();
    WxCpUserExternalContactInfo result = this.wxCpService.getExternalContactService().getExternalContact(externalUserId);
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testAddContactWay() throws WxErrorException {

    final String concatUserId = "符合要求的userId";

    WxCpContactWayInfo info = new WxCpContactWayInfo();
    info.setType(WxCpContactWayInfo.TYPE.SIGLE);
    info.setScene(WxCpContactWayInfo.SCENE.MINIPROGRAM);
    info.setUsers(Lists.newArrayList(concatUserId));
    info.setRemark("CreateDate:" + DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date()));
    this.wxCpService.getExternalContactService().addContactWay(info);
  }

  @Test
  public void testGetContactWay() throws WxErrorException {
    final String configId = "2d7a68c657663afbd1d90db19a4b5ee9";
    WxCpContactWayInfo contactWayInfo = this.wxCpService.getExternalContactService().getContactWay(configId);
    System.out.println(contactWayInfo);
    assertNotNull(contactWayInfo);
  }

  @Test
  public void testUpdateContactWay() throws WxErrorException {
    final String configId = "2d7a68c657663afbd1d90db19a4b5ee9";
    final String concatUserId = "符合要求的userId";
    WxCpContactWayInfo info = new WxCpContactWayInfo();
    info.setConfigId(configId);
    info.setUsers(Lists.newArrayList(concatUserId));
    info.setRemark("CreateDate:" + DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date()));
    WxCpBaseResp resp = this.wxCpService.getExternalContactService().updateContactWay(info);
    System.out.println(resp);
    assertNotNull(resp);
  }

  @Test
  public void testDelContactWay() throws WxErrorException {
    final String configId = "2d7a68c657663afbd1d90db19a4b5ee9";
    WxCpBaseResp resp = this.wxCpService.getExternalContactService().deleteContactWay(configId);
    System.out.println(resp);
    assertNotNull(resp);
  }

  @Test
  public void testCloseTempChat() throws WxErrorException {
    final String externalUserId = "externalUserId";
    WxCpBaseResp resp = this.wxCpService.getExternalContactService().closeTempChat(userId, externalUserId);
    System.out.println(resp);
  }

  @Test
  public void testListExternalContacts() throws WxErrorException {
    String userId = this.configStorage.getUserId();
    List<String> ret = this.wxCpService.getExternalContactService().listExternalContacts(userId);
    System.out.println(ret);
    assertNotNull(ret);
  }

  @Test
  public void testListExternalWithPermission() throws WxErrorException {
    List<String> ret = this.wxCpService.getExternalContactService().listFollowers();
    System.out.println(ret);
    assertNotNull(ret);
  }

  @Test
  public void testGetContactDetail() throws WxErrorException {
    String externalUserId = this.configStorage.getExternalUserId();
    WxCpUserExternalContactInfo result = this.wxCpService.getExternalContactService().getContactDetail(externalUserId);
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testGetCorpTagList() throws WxErrorException {
    String tag[]={};
    WxCpUserExternalTagGroupList result = this.wxCpService.getExternalContactService().getCorpTagList(null);
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testAddCorpTag() throws WxErrorException {

    List<WxCpUserExternalTagGroupInfo.Tag> list = new ArrayList<>();
    WxCpUserExternalTagGroupInfo.Tag  tag = new  WxCpUserExternalTagGroupInfo.Tag();
    tag.setName("测试标签9");
    tag.setOrder(1);
    list.add(tag);

    WxCpUserExternalTagGroupInfo tagGroupInfo = new WxCpUserExternalTagGroupInfo();
    WxCpUserExternalTagGroupInfo.TagGroup tagGroup = new WxCpUserExternalTagGroupInfo.TagGroup();
    tagGroup.setGroupName("其他");
    tagGroup.setOrder(1);
    tagGroup.setTag(list);
    tagGroupInfo.setTagGroup(tagGroup);

    WxCpUserExternalTagGroupInfo result = this.wxCpService.getExternalContactService().addCorpTag(tagGroupInfo);

    System.out.println(result.toJson());
    assertNotNull(result);
  }

  @Test
  public void testEditCorpTag() throws WxErrorException {

    WxCpBaseResp result = this.wxCpService.getExternalContactService().editCorpTag("et2omCCwAA6PtGsfeEOQMENl3Ub1FA6A", "未知6", 2);

    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testDelCorpTag() throws WxErrorException {

    String tagId[] = {"et2omCCwAA6PtGsfeEOQMENl3Ub1FA6A"};
    String groupId[] = {};

    WxCpBaseResp result = this.wxCpService.getExternalContactService().delCorpTag(tagId,groupId);

    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testMarkTag() throws WxErrorException {

    String userid="HuangXiaoMing";
    String externalUserid="wo2omCCwAAzR0Rt1omz-90o_XJkPGXIQ";
    String addTag[] = {"et2omCCwAAzdcSK-RV80YS9sbpCXlNlQ"};
    String removeTag[] = {};

    WxCpBaseResp result = this.wxCpService.getExternalContactService().markTag(userid,externalUserid,addTag,removeTag);

    System.out.println(result);
    assertNotNull(result);
  }

}
