package com.company;

import java.util.Objects;

public class Source
{
    private String ip;
    private int port;


    public Source(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }


    public int getPort()
    {
        return port;
    }

    public String getIp()
    {
        return ip;
    }

    public boolean equals(Object o)
    {
        if(o instanceof Source)
        {
            if(((Source)o).getIp().equals(this.getIp()) && ((Source)o).getPort() == this.getPort())
                return true;
            else
                return false;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ip, port);
    }

//    @Override
//    public boolean equals(Object o)
//    {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Source source = (Source) o;
//        return port == source.port &&
//                Objects.equals(ip, source.ip);
//    }
}
