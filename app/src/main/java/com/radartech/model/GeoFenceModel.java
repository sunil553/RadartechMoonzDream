/*
 *
 *                  Moonzdream Confidential and Proprietary
 *
 *    This work contains valuable confidential and proprietary information.
 *    Disclosure, use or reproduction outside of Moonzdream, Inc. is prohibited
 *    except as authorized in writing. This unpublished work is protected by
 *    the laws of the United States and other countries. If publication occurs,
 *    following notice shall apply:
 *
 *                        Copyright 2016, Moonzdream Inc.
 *                            All rights reserved.
 *                   Freedom of Information Act(5 USC 522) and
 *            Disclosure of Confidential Information Generaly(18 USC 1905)
 *
 *    This material is being furnished in confidence by Moonzdream, Inc. The
 *    information disclosed here falls within Exemption (b)(4) of 5 USC 522
 *    and the prohibitions of 18 USC 1905
 */

package com.radartech.model;

/**
 * Created by sandeep on 30-09-2016.
 */

public class GeoFenceModel
{
    private String shapeparam;

    private String isopen;

    private String phonenumber;

    private String efencename;

    private String efenceid;

    private String shapetype;

    private String alarmtype;

    public String getShapeparam ()
    {
        return shapeparam;
    }

    public void setShapeparam (String shapeparam)
    {
        this.shapeparam = shapeparam;
    }

    public String getIsopen ()
    {
        return isopen;
    }

    public void setIsopen (String isopen)
    {
        this.isopen = isopen;
    }

    public String getPhonenumber ()
    {
        return phonenumber;
    }

    public void setPhonenumber (String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getEfencename ()
    {
        return efencename;
    }

    public void setEfencename (String efencename)
    {
        this.efencename = efencename;
    }

    public String getEfenceid ()
    {
        return efenceid;
    }

    public void setEfenceid (String efenceid)
    {
        this.efenceid = efenceid;
    }

    public String getShapetype ()
    {
        return shapetype;
    }

    public void setShapetype (String shapetype)
    {
        this.shapetype = shapetype;
    }

    public String getAlarmtype ()
    {
        return alarmtype;
    }

    public void setAlarmtype (String alarmtype)
    {
        this.alarmtype = alarmtype;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [shapeparam = "+shapeparam+", isopen = "+isopen+", phonenumber = "+phonenumber+", efencename = "+efencename+", efenceid = "+efenceid+", shapetype = "+shapetype+", alarmtype = "+alarmtype+"]";
    }
}


