<?php
class Category extends CI_Model {

    public function __construct()	{
        $this->load->database(); 
    }

    public function insert_category($category,$userid){
        if($this->db->query("INSERT INTO categories(category,userid) 
                        VALUES('{$category}',{$userid})")){
            $result = $this->getQueryResult("SELECT c.*,u.username FROM categories c INNER JOIN users u on u.id=c.userid WHERE c.id=(SELECT max(cc.id) FROM categories cc)");
            $obj = $this->encodeResult("200","Success","categories",$result);
        }
        else{
            $obj = $this->encodeResult("404","Error","categories",new stdClass);
        }
        return $obj;
    }

    public function delete_category($catid){

        $result = $this->getQueryResult("SELECT c.*,u.username FROM categories c INNER JOIN users u on u.id=c.userid WHERE c.id={$catid}");
        if($this->db->query("DELETE FROM categories WHERE id={$catid}")){
            $obj = $this->encodeResult("200","Success","categories",$result);
        }
        else{
            $obj = $this->encodeResult("404","Error","categories",new stdClass);
        }

        return $obj;
    }
    public function get_categories(){
        $result = $this->getQueryResult("SELECT c.*,u.username FROM categories c INNER JOIN users u on u.id=c.userid");
        $obj = $this->encodeResult("200","Success","categories",$result);
        return $obj;
    }

    public function encodeResult($type,$msg,$objectType,$data){
        return json_encode(array("type" => $type, "message" => $msg, $objectType => $data));
    }

    private function getQueryResult($query){
        return $this->db->query($query)->result();
    }
}
?>