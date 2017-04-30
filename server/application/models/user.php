<?php
class User extends CI_Model {


    public function __construct()	{
        $this->load->database(); 
        $this->app_secret = "12b5484796c47df99faf71bda8c03f15";
    }

    public function sign_up($username,$password,$app_secret_new){

        if($this->app_secret == $app_secret_new){

            $access_token = $this->create_access_token();
            $encoded_password = $this->convert_password($password);

            $this->db->query("INSERT INTO Users(username,password,access_token) 
                                 VALUES('{$username}','{$encoded_password}','{$access_token}')");
        }

    }

    public function verfiyUserCall($access_token,$id){
        $data = $this->getQueryResult("SELECT id FROM Users WHERE id={$id} AND access_token='{$access_token}'");

        if(count($data) == 0){
            return false;
        }

        return true;
    }    

    public function login($username,$password){


        $password = $this->convert_password($password);

        $data = $this->getQueryResult("SELECT id,user_type FROM Users WHERE username='{$username}' AND password='{$password}'");

        if(count($data) == 0){
            return $this->encodeResult("404","Username or Password incorrect","users",new stdClass);
        }

        $new_access_token = $this->create_access_token();
        
        $this->db->query("UPDATE Users SET access_token='{$new_access_token}' WHERE id={$data[0]->id}");

        $obj = new stdClass;
        $obj->access_token = $new_access_token;
        $extended = (object) array_merge((array)$obj, (array)$data[0]);


        return $this->encodeResult("200","Success","users",$extended);
    }

    public function encodeResult($type,$msg,$objectType,$data){
        return json_encode(array("type" => $type, "message" => $msg, $objectType => $data));
    }

    private function getQueryResult($query){
        return $this->db->query($query)->result();
    }

    private function convert_password($password){
        return md5($password);
    }

    private function create_access_token(){
        return md5(date("Y-m-d H:i:s"));
    }



}
?>