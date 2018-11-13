# THIS FILE DESCRIBES THE FORMATS OF THE JSON MESSAGES THAT ARE USED TO TRANSFER INFORMATION

-----------------------------

# REMOTE SERVER

## From Nebulon:

1. Liquid level message

```
{
    "message_size" : ###(int),
    "message_type" : "neb_to_server_llu",
    "nebulon_id" : ##(int),
    "nebulon_liquid_level" : ###(int)
}
```

  * neb_to_server_llu stands for nebulon to server liquid level update

2. Private ip address message

```
{
    "message_size" : ###(int),
    "message_type" : "neb_to_server_ipu",
    "nebulon_id" : ##(int),
    "nebulon_ip_address" : "##.##.##.##"
}
```

  * neb_to_server_ipu stands for nebulon to server ip address update
  * ##.##.##.## stands for the ip address of the nebulizer

## From Mobile App

1. Mobile App requests private IP addresses of the specified nebulons

```
{
    "message_size" : ###(int),
    "message_type" : "app_to_server_ipr",
    "nebulons_ids" : [##(int), ...]
}
```

  * app_to_server_ipr stands for nebulon to server ip address request

2. Mobile app requests user authentication

```
{
    "message_size":##,
    "message_type":"app_to_server_logr",
    "username":"UserName123",
    "password":"PassW0RD"
}
```

----------------------------------------------

# NEBULON SERVER

## From Mobile App

1. Mobile App sends script execution order, with the proper Network Parameters

```
{
    "message_size" : ##(int),
    "message_type" : "app_to_neb_net",
    "network" : {
	"security_type" : ##(0 or 1 or 2),
	"ssid" : "NetworkName",
	"user" : "NetworkUserName", //only network_type 2
	"password" : "NetworkPassword" // only network_type 1 and 2 
    }
}
```

  * app_to_neb_net stands for app to nebulon network information
  * network_type : 0 stands for no security, 1 stands for WPA security,
                   2 stands for EAP security

2. Mobile App sends a schedule to be interpreted

```
{
    "message_size" : 480,
    "message_type" : "app_to_neb_sch",
    "schedule" : [
	{
	    "monday" : [
		{
		    "start" : "10:00",
		    "end" : "18:00",
		    "working_time" : "02:00",
		    "sleeping_time" : "00:05",
		    "schedule_type" : 2
		    
		},
		{
		    "start": "19:00",
		    "end" : "22:00",
		    "working_time" : "03:00",
		    "sleeping_time" : "00:00",
		    "schedule_type" : 2
	            
		}
	    ]
	},
	{
	    "tuesday" : // ....
	}
    ]
}
```

  * schedule_type inficates the fragrance to emit.
    0: fragrance 1
    1: fragrance 2
    2: both of them

--------------------------------------------------------------

# MOBILE APP

## From Remote server

1. Ip addresses that were requested before

```
{
    "message_size" : ###(int),
    "message_type" : "serv_to_app_ips",
    "nebulons_ips" : [
	{
	    "neb_id" : ##(int),
	    "neb_ip" : "##.##.##.##"
	},
	{
	    "neb_id" : ##(int),
	    "neb_ip" : "##.##.##.##"
	},
	{
	    // more ip addresses if needed
	}
    ]
}
```

  * serv_to_app_ips stands for server to app ip addresses
  * ##.##.##.## indicates the nebulon's private IP(v4 or v6)
  
## From Nebulon

1. Nebulon's ID that is sent when the Nebulon gets connected to the app Hotspot

```
{
    "message_size" : ###(int),
    "message_type" : "neb_to_app_idp",
    "nebulon_id" : ##(int)
}
```

  * neb_to_app_idp stands for nebulon to app id response