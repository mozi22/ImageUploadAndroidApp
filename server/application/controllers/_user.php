<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class _User extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

	public function sign_up()
	{
		$username = $this->input->post('username');
		$password = $this->input->post('password');
		$app_secret = $this->input->post('app_secret');
		

		$this->load->model("user");
		$this->user->sign_up($username,$password,$app_secret);
	}

	public function login()
	{
		$username = $this->input->post('username');
		$password = $this->input->post('password');

		$this->load->model("user");
		echo $this->user->login($username,$password);
	}
}
