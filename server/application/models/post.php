<?php
class Post extends CI_Model {

    public function __construct()	{
        $this->load->database();
        $this->load->helper('url');
    }

    public function image_path_tester(){
        echo base_url('uploads/');
    }

    public function insert_post($original_price,$retail_price,$userid,$categoryid,$rotationCount){

        $image_path = realpath(APPPATH . '../uploads');
        $config['upload_path']          =  $image_path;
        $config['allowed_types']        = 'gif|jpg|png';

        $this->load->library('upload',$config);


        if ( ! $this->upload->do_upload('image'))
        {
                $error = array('error' => $this->upload->display_errors());
                return $this->encodeResult("400","Image can't be uploaded","posts",array($error));
                
        }
        $data = array('upload_data' => $this->upload->data());
        $image_name = $data['upload_data']['file_name'];

        if($this->db->query("INSERT INTO posts(userid,categoryid,image_path,original_price,retail_price,rotation) VALUES ({$userid},{$categoryid},'{$image_name}',{$original_price},{$retail_price},{$rotationCount})")){
            return $this->encodeResult("200","Success","posts",array());
        }
        else{
            return $this->encodeResult("400","Data couldn't be inserted","posts",array());            
        }
    }


    public function get_posts_with_filters($date,$category){

        /** This will run only when the first time user loads the main activity */
        if($category == "firstTime"){
            $query = "SELECT p.*,c.category FROM posts p INNER JOIN categories c ON c.id=p.categoryid
                                            WHERE DATE_FORMAT(p.created_at,'%b %d %Y')=(
                                            SELECT DATE_FORMAT(created_at,'%b %d %Y') FROM posts p ORDER BY id DESC LIMIT 1) ORDER BY p.id DESC";
        }

        /** This will run when user selects the all category with a specific date*/
        else if($category == "All"){
            $query = "SELECT p.*,c.category FROM posts p INNER JOIN categories c ON c.id=p.categoryid
                                            WHERE DATE_FORMAT(p.created_at,'%b %d %Y')='{$date}' ORDER BY p.id DESC";
        }

        /** This will run when user applies filter w.r.t date and category both */
        else{
            $query = "SELECT p.*,c.category FROM posts p INNER JOIN categories c ON c.id=p.categoryid
                                            WHERE DATE_FORMAT(p.created_at,'%b %d %Y')='{$date}' AND p.categoryid={$category} ORDER BY p.id DESC";
        }


        $result = $this->getQueryResult($query);

        $obj = $this->encodeResult("200","Success","posts",$result);
        return $obj;
    }

    public function get_dates(){

        $result = $this->getQueryResult("SELECT DISTINCT(DATE_FORMAT(created_at,'%b %d %Y')) as created_at FROM posts");
        $obj = $this->encodeResult("200","Success","posts",$result);
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