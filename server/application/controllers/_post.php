<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class _Post extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }


	public function insert_post()
	{
		$this->load->model("post");
		$this->load->model("user");

		$retail_price = $this->input->post('retail_price');
		$original_price = $this->input->post('original_price');
		$userid = $this->input->post('userid');
		$access_token = $this->input->post('access_token');
		$categoryid = $this->input->post('categoryid');
		$rotationCount = $this->input->post('rotationCount');

		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->post->insert_post($original_price,$retail_price,$userid,$categoryid,$rotationCount);
		}
		else{
			echo $this->user->encodeResult("400","Call not verified","posts",new stdClass);
		}
	}

	public function get_posts_with_filters(){

		$this->load->model("post");
		$this->load->model("user");

		$userid = $this->input->post('userid');
		$access_token = $this->input->post('access_token');
		$date = $this->input->post('date');
		$category = $this->input->post('categoryid');


		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->post->get_posts_with_filters($date,$category);
		}
		else{
			echo $this->user->encodeResult("400","Call not verified","posts",new stdClass);
		}
	}

	public function get_dates(){

		$this->load->model("post");
		$this->load->model("user");

		$userid = $this->input->get('userid');
		$access_token = $this->input->get('access_token');

		if($this->user->verfiyUserCall($access_token,$userid)){
			echo $this->post->get_dates();
		}
		else{
			echo $this->user->encodeResult("400","Call not verified","posts",new stdClass);
		}
	}

	public function image_path_tester(){

		$this->load->model("post");
		$this->post->image_path_tester();
	}
}
