<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class _Category extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

	public function insert_category()
	{
		$category = $this->input->post('category');
		$userid = $this->input->post('userid');
		$access_token = $this->input->post('access_token');


		$this->load->model("user");
		$this->load->model("category");

		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->category->insert_category($category,$userid);
		}
		else{
			echo $this->category->encodeResult("400","Call not verified","categories",new stdClass);
		}
	}

	public function delete_category(){

		$this->load->model("user");
		$this->load->model("category");

		$categoryid = $this->input->post('categoryid');
		$userid = $this->input->post('userid');
		$access_token = $this->input->post('access_token');

		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->category->delete_category($categoryid);
		}
		else{
			echo $this->category->encodeResult("400","Call not verified","categories",new stdClass);
		}
	}
	public function get_categories(){

		$this->load->model("user");
		$this->load->model("category");

		$userid = $this->input->get('userid');
		$access_token = $this->input->get('access_token');

		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->category->get_categories();
		}
		else{
			echo $this->category->encodeResult("400","Call not verified","categories",new stdClass);
		}
	}

}
