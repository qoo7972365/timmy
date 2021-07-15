-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 11, 2018 at 04:45 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `picapoint`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_offices`
--

CREATE TABLE `tbl_offices` (
  `id` int(10) NOT NULL,
  `off_name` varchar(100) NOT NULL,
  `address` varchar(230) NOT NULL,
  `city` varchar(100) NOT NULL,
  `ph_no` varchar(20) NOT NULL,
  `office_time` varchar(100) NOT NULL,
  `contact_person` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_offices`
--

INSERT INTO `tbl_offices` (`id`, `off_name`, `address`, `city`, `ph_no`, `office_time`, `contact_person`) VALUES
(5, 'Line 1 - Lanao Del Norte', 'Kiwalan Cove, Dalipuga, Iligan City, Lanao del Norte', 'Iligan ', '063225131', '5:00 AM - 8:15 PM', 'Juan Dela Cruz'),
(6, 'Line 2 - Capas, Tarlac', 'Sto. Domingo II, Capas, Tarlac, 2315 Philippines', 'Tarlac City', '9250505', '5:00 AM - 8:15 PM', 'Francis Reyes'),
(7, 'Line 3 - Concepcion, Tarlac', 'San Agustin Concepcion Tarlac, 2316 Philippines', 'Tarlac City', '9250505', '5:00 AM - 8:15 PM', 'Jose Mendoza');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_picapoint`
--

CREATE TABLE `tbl_picapoint` (
  `cid` int(10) NOT NULL,
  `cons_no` varchar(20) NOT NULL,
  `ship_name` varchar(100) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `s_add` varchar(200) NOT NULL,
  `rev_name` varchar(100) NOT NULL,
  `r_phone` varchar(12) NOT NULL,
  `r_add` varchar(200) NOT NULL,
  `type` varchar(40) NOT NULL,
  `weight` double NOT NULL,
  `invice_no` varchar(20) NOT NULL,
  `qty` int(10) NOT NULL,
  `book_mode` varchar(20) NOT NULL,
  `freight` double NOT NULL,
  `mode` varchar(20) NOT NULL,
  `pick_date` varchar(20) NOT NULL,
  `pick_time` varchar(10) NOT NULL,
  `status` varchar(20) NOT NULL,
  `comments` varchar(250) NOT NULL,
  `book_date` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_picapoint`
--

INSERT INTO `tbl_picapoint` (`cid`, `cons_no`, `ship_name`, `phone`, `s_add`, `rev_name`, `r_phone`, `r_add`, `type`, `weight`, `invice_no`, `qty`, `book_mode`, `freight`, `mode`, `pick_date`, `pick_time`, `status`, `comments`, `book_date`) VALUES
(7, 'X9R9KURD0M', 'Line 2 - Concepcion, Tarlac', '9250505', 'San Agustin Concepcion Tarlac, 2316 Philippines', 'Erika Ritzelle Bondoc', '09178351160', 'Dagupan Extension, Tondo, Manila', 'Sack of Rice', 1000, '3', 2, 'Credit Card', 300, 'Road', '16/11/2018', '1:00 PM', 'In Transit', 'On process.', '2018-11-11'),
(6, 'PFJQQU4Z81', 'Tarlac Pilmico', '9250505', 'Sto Rosario Street 2135, Capas Tarlac', 'Kimberly C. Bito-on', '09568125307', 'Arlegui Petron Service Center, Arlegui Street, Quiapo, Manila, Metro Manila, Philippines', 'Sack of Flour', 500, '2', 5, 'Paid', 150, 'Road', '21/11/2018', '10:00 AM', 'In Transit', 'Your product is on its way', '2018-11-11'),
(5, 'G1UG0M7ZVF', 'Line 1', '10093298', 'Kiwalan Cove, Dalipuga, Iligan City, Lanao del Norte', 'John Eddie Donalvo Macias', '09107021150', 'Tondo, Manila, Metro Manila, Philippines', 'Sack of Flour', 500, '1', 1, 'Paid', 1, 'Road', '12/11/2018', '10:00 AM', 'Completed', 'Your Product is in Warehouse.', '2018-11-11'),
(8, '6POJ7VT9TS', 'Line 3 - Conception, Tarlact', '09457190398', 'Tarlac', 'Christopher John B. Mora', '09457190398', '412 A Reyes St. Villa Miguela Pinagbuhatan Pasig City', 'Sack of Flour', 500, '3', 900, 'Credit Card', 300, 'Sea', '18/04/2019', '10:00 AM', 'In Transit', 'Thank you', '2018-11-11');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_picapoint_officers`
--

CREATE TABLE `tbl_picapoint_officers` (
  `cid` int(10) NOT NULL,
  `officer_name` varchar(40) NOT NULL,
  `off_pwd` varchar(40) NOT NULL,
  `address` varchar(250) NOT NULL,
  `email` varchar(100) NOT NULL,
  `ph_no` varchar(12) NOT NULL,
  `office` varchar(100) NOT NULL,
  `reg_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_picapoint_officers`
--

INSERT INTO `tbl_picapoint_officers` (`cid`, `officer_name`, `off_pwd`, `address`, `email`, `ph_no`, `office`, `reg_date`) VALUES
(4, 'Juan Dela Cruz', 'juan', '902 Kagitingan St., Road 10, Tondo, Manila', 'juandelacruz@gmail.com', '89819313', 'Line 1 - Lanao Del Norte', '2018-11-11 07:34:05');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_picapoint_track`
--

CREATE TABLE `tbl_picapoint_track` (
  `id` int(10) NOT NULL,
  `cid` int(10) NOT NULL,
  `cons_no` varchar(20) NOT NULL,
  `current_city` varchar(100) NOT NULL,
  `status` varchar(30) NOT NULL,
  `comments` varchar(255) NOT NULL,
  `bk_time` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_picapoint_track`
--

INSERT INTO `tbl_picapoint_track` (`id`, `cid`, `cons_no`, `current_city`, `status`, `comments`, `bk_time`) VALUES
(10, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:17:13'),
(9, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:17:08'),
(8, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:14:52'),
(7, 5, 'G1UG0M7ZVF', 'Line 1', 'Landed', 'Your product has been delivered. Thank you!', '2018-11-11 08:13:20'),
(11, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:17:27'),
(12, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:21:36'),
(13, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:23:15'),
(14, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:26:13'),
(15, 5, 'G1UG0M7ZVF', 'Line 1', 'Landed', '', '2018-11-11 08:26:34'),
(16, 5, 'G1UG0M7ZVF', 'Line 1', 'OnWareHouse', '', '2018-11-11 08:28:47'),
(17, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:29:48'),
(18, 5, 'G1UG0M7ZVF', 'Line 1', 'In Transit', '', '2018-11-11 08:29:57'),
(19, 5, 'G1UG0M7ZVF', 'Line 1', 'Completed', '', '2018-11-11 08:31:39'),
(20, 5, 'G1UG0M7ZVF', 'Line 1', 'Completed', 'Your product has been delivered.', '2018-11-11 08:32:53');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_feedbacks`
--

CREATE TABLE `tbl_user_feedbacks` (
  `id` int(11) NOT NULL,
  `delivery_satisfaction` varchar(100) NOT NULL,
  `delivery_time` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_user_feedbacks`
--

INSERT INTO `tbl_user_feedbacks` (`id`, `delivery_satisfaction`, `delivery_time`) VALUES
(1, 'no', 'yes'),
(2, 'yes', 'yes'),
(3, 'no', 'no'),
(4, 'yes', 'yes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_offices`
--
ALTER TABLE `tbl_offices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_picapoint`
--
ALTER TABLE `tbl_picapoint`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `tbl_picapoint_officers`
--
ALTER TABLE `tbl_picapoint_officers`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `tbl_picapoint_track`
--
ALTER TABLE `tbl_picapoint_track`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user_feedbacks`
--
ALTER TABLE `tbl_user_feedbacks`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_offices`
--
ALTER TABLE `tbl_offices`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tbl_picapoint`
--
ALTER TABLE `tbl_picapoint`
  MODIFY `cid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tbl_picapoint_officers`
--
ALTER TABLE `tbl_picapoint_officers`
  MODIFY `cid` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_picapoint_track`
--
ALTER TABLE `tbl_picapoint_track`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tbl_user_feedbacks`
--
ALTER TABLE `tbl_user_feedbacks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
