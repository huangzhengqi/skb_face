

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area_info
-- ----------------------------
DROP TABLE IF EXISTS `area_info`;
CREATE TABLE `area_info`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `area_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `area_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `province_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area_info
-- ----------------------------
INSERT INTO `area_info` VALUES ('1981', '440704', '江海区', '206', '19');
INSERT INTO `area_info` VALUES ('2842', '610729', '留坝县', '300', '27');
INSERT INTO `area_info` VALUES ('1862', '430723', '澧　县', '192', '18');
INSERT INTO `area_info` VALUES ('1928', '433127', '永顺县', '199', '18');
INSERT INTO `area_info` VALUES ('24', '120106', '红桥区', '366', '2');
INSERT INTO `area_info` VALUES ('2599', '530629', '威信县', '275', '25');
INSERT INTO `area_info` VALUES ('2201', '469003', '儋州市', '237', '21');
INSERT INTO `area_info` VALUES ('1079', '340881', '桐城市', '108', '12');
INSERT INTO `area_info` VALUES ('1932', '440103', '荔湾区', '200', '19');
INSERT INTO `area_info` VALUES ('939', '330206', '北仑区', '91', '11');
INSERT INTO `area_info` VALUES ('590', '220281', '蛟河市', '54', '7');
INSERT INTO `area_info` VALUES ('638', '222406', '和龙市', '61', '7');
INSERT INTO `area_info` VALUES ('1853', '430624', '湘阴县', '191', '18');
INSERT INTO `area_info` VALUES ('1867', '430801', '市辖区', '193', '18');
INSERT INTO `area_info` VALUES ('182', '130903', '运河区', '13', '3');
INSERT INTO `area_info` VALUES ('165', '130731', '涿鹿县', '11', '3');
INSERT INTO `area_info` VALUES ('36', '120225', '蓟　县', '366', '2');
INSERT INTO `area_info` VALUES ('879', '320826', '涟水县', '84', '10');
INSERT INTO `area_info` VALUES ('399', '150623', '鄂托克前旗', '32', '5');
INSERT INTO `area_info` VALUES ('2186', '451402', '江洲区', '234', '20');
INSERT INTO `area_info` VALUES ('578', '220106', '绿园区', '53', '7');
INSERT INTO `area_info` VALUES ('1600', '410923', '南乐县', '163', '16');
INSERT INTO `area_info` VALUES ('1768', '421125', '浠水县', '181', '17');
INSERT INTO `area_info` VALUES ('1266', '360426', '德安县', '130', '14');
INSERT INTO `area_info` VALUES ('1979', '440701', '市辖区', '206', '19');
INSERT INTO `area_info` VALUES ('1634', '411328', '唐河县', '167', '16');
INSERT INTO `area_info` VALUES ('1873', '430902', '资阳区', '194', '18');
INSERT INTO `area_info` VALUES ('2044', '441781', '阳春市', '214', '19');
INSERT INTO `area_info` VALUES ('1452', '371203', '钢城区', '149', '15');
INSERT INTO `area_info` VALUES ('111', '130525', '隆尧县', '9', '3');
INSERT INTO `area_info` VALUES ('412', '150727', '新巴尔虎右旗', '33', '5');
INSERT INTO `area_info` VALUES ('1963', '440403', '斗门区', '203', '19');
INSERT INTO `area_info` VALUES ('2047', '441821', '佛冈县', '215', '19');
INSERT INTO `area_info` VALUES ('2558', '530126', '石林彝族自治县', '271', '25');
INSERT INTO `area_info` VALUES ('648', '230109', '松北区', '62', '8');
INSERT INTO `area_info` VALUES ('574', '220102', '南关区', '53', '7');
INSERT INTO `area_info` VALUES ('201', '131023', '永清县', '14', '3');
INSERT INTO `area_info` VALUES ('280', '140722', '左权县', '368', '4');
INSERT INTO `area_info` VALUES ('1887', '431026', '汝城县', '195', '18');
INSERT INTO `area_info` VALUES ('1769', '421126', '蕲春县', '181', '17');
INSERT INTO `area_info` VALUES ('128', '130621', '满城县', '10', '3');
INSERT INTO `area_info` VALUES ('1936', '440107', '芳村区', '200', '19');
INSERT INTO `area_info` VALUES ('592', '220283', '舒兰市', '54', '7');
INSERT INTO `area_info` VALUES ('1450', '371201', '市辖区', '149', '15');
INSERT INTO `area_info` VALUES ('520', '210781', '凌海市', '45', '6');
INSERT INTO `area_info` VALUES ('913', '321283', '泰兴市', '88', '10');
INSERT INTO `area_info` VALUES ('3110', '653221', '和田县', '341', '31');
INSERT INTO `area_info` VALUES ('2802', '610429', '旬邑县', '297', '27');
INSERT INTO `area_info` VALUES ('633', '222401', '延吉市', '61', '7');
INSERT INTO `area_info` VALUES ('756', '231024', '东宁县', '71', '8');
INSERT INTO `area_info` VALUES ('2238', '500227', '璧山县', '239', '22');
INSERT INTO `area_info` VALUES ('760', '231084', '宁安市', '71', '8');
INSERT INTO `area_info` VALUES ('2829', '610630', '宜川县', '299', '27');
INSERT INTO `area_info` VALUES ('2467', '520181', '清镇市', '262', '24');
INSERT INTO `area_info` VALUES ('1886', '431025', '临武县', '195', '18');
INSERT INTO `area_info` VALUES ('3054', '650202', '独山子区', '332', '31');
INSERT INTO `area_info` VALUES ('330', '141031', '隰　县', '25', '4');
INSERT INTO `area_info` VALUES ('2754', '542623', '米林县', '293', '26');
INSERT INTO `area_info` VALUES ('1803', '430124', '宁乡县', '186', '18');
INSERT INTO `area_info` VALUES ('700', '230505', '四方台区', '66', '8');
INSERT INTO `area_info` VALUES ('546', '211102', '双台子区', '49', '6');
INSERT INTO `area_info` VALUES ('1814', '430281', '醴陵市', '187', '18');
INSERT INTO `area_info` VALUES ('462', '210104', '大东区', '39', '6');
INSERT INTO `area_info` VALUES ('581', '220181', '九台市', '53', '7');
INSERT INTO `area_info` VALUES ('895', '321003', '邗江区', '86', '10');
INSERT INTO `area_info` VALUES ('2392', '511821', '名山县', '256', '23');
INSERT INTO `area_info` VALUES ('335', '141082', '霍州市', '25', '4');
INSERT INTO `area_info` VALUES ('2266', '510114', '新都区', '241', '23');
INSERT INTO `area_info` VALUES ('2598', '530628', '彝良县', '275', '25');
INSERT INTO `area_info` VALUES ('1924', '433123', '凤凰县', '199', '18');
INSERT INTO `area_info` VALUES ('2361', '511422', '彭山县', '252', '23');
INSERT INTO `area_info` VALUES ('2295', '510521', '泸　县', '244', '23');
INSERT INTO `area_info` VALUES ('2435', '513334', '理塘县', '260', '23');
INSERT INTO `area_info` VALUES ('136', '130629', '容城县', '10', '3');
INSERT INTO `area_info` VALUES ('275', '140623', '右玉县', '21', '4');
INSERT INTO `area_info` VALUES ('1842', '430525', '洞口县', '190', '18');
INSERT INTO `area_info` VALUES ('2298', '510525', '古蔺县', '244', '23');
INSERT INTO `area_info` VALUES ('1235', '350926', '柘荣县', '126', '13');
INSERT INTO `area_info` VALUES ('750', '230921', '勃利县', '70', '8');
INSERT INTO `area_info` VALUES ('1010', '331081', '温岭市', '99', '11');
INSERT INTO `area_info` VALUES ('307', '140923', '代　县', '24', '4');
INSERT INTO `area_info` VALUES ('2848', '610823', '横山县', '301', '27');
INSERT INTO `area_info` VALUES ('2187', '451421', '扶绥县', '234', '20');
INSERT INTO `area_info` VALUES ('714', '230623', '林甸县', '67', '8');
INSERT INTO `area_info` VALUES ('2246', '500235', '云阳县', '239', '22');
INSERT INTO `area_info` VALUES ('2220', '500103', '渝中区', '238', '22');
INSERT INTO `area_info` VALUES ('1948', '440224', '仁化县', '201', '19');
INSERT INTO `area_info` VALUES ('2357', '511381', '阆中市', '251', '23');
INSERT INTO `area_info` VALUES ('522', '210801', '市辖区', '46', '6');
INSERT INTO `area_info` VALUES ('2455', '513436', '美姑县', '261', '23');
INSERT INTO `area_info` VALUES ('114', '130528', '宁晋县', '9', '3');
INSERT INTO `area_info` VALUES ('2287', '510403', '西　区', '243', '23');
INSERT INTO `area_info` VALUES ('1329', '361025', '乐安县', '136', '14');
INSERT INTO `area_info` VALUES ('1881', '431003', '苏仙区', '195', '18');
INSERT INTO `area_info` VALUES ('531', '210903', '新邱区', '47', '6');
INSERT INTO `area_info` VALUES ('277', '140701', '市辖区', '368', '4');
INSERT INTO `area_info` VALUES ('467', '210113', '新城子区', '39', '6');
INSERT INTO `area_info` VALUES ('2275', '510182', '彭州市', '241', '23');
INSERT INTO `area_info` VALUES ('1282', '360722', '信丰县', '133', '14');
INSERT INTO `area_info` VALUES ('2738', '542424', '聂荣县', '291', '26');
INSERT INTO `area_info` VALUES ('1134', '341722', '石台县', '116', '12');
INSERT INTO `area_info` VALUES ('1202', '350624', '诏安县', '123', '13');
INSERT INTO `area_info` VALUES ('1160', '350205', '海沧区', '119', '13');
INSERT INTO `area_info` VALUES ('1509', '410105', '金水区', '155', '16');
INSERT INTO `area_info` VALUES ('1362', '370205', '四方区', '139', '15');
INSERT INTO `area_info` VALUES ('1905', '431221', '中方县', '197', '18');
INSERT INTO `area_info` VALUES ('511', '210624', '宽甸满族自治县', '44', '6');
INSERT INTO `area_info` VALUES ('105', '130502', '桥东区', '9', '3');
INSERT INTO `area_info` VALUES ('2653', '532624', '麻栗坡县', '281', '25');
INSERT INTO `area_info` VALUES ('1670', '411701', '市辖区', '171', '16');
INSERT INTO `area_info` VALUES ('3013', '632801', '格尔木市', '325', '29');
INSERT INTO `area_info` VALUES ('3124', '654026', '昭苏县', '342', '31');
INSERT INTO `area_info` VALUES ('1208', '350681', '龙海市', '123', '13');
INSERT INTO `area_info` VALUES ('2744', '542430', '尼玛县', '291', '26');
INSERT INTO `area_info` VALUES ('1610', '411082', '长葛市', '164', '16');
INSERT INTO `area_info` VALUES ('3099', '653122', '疏勒县', '340', '31');
INSERT INTO `area_info` VALUES ('1311', '360881', '井冈山市', '134', '14');
INSERT INTO `area_info` VALUES ('3169', '', '北区', '374', '33');
INSERT INTO `area_info` VALUES ('1326', '361022', '黎川县', '136', '14');
INSERT INTO `area_info` VALUES ('2321', '510822', '青川县', '247', '23');
INSERT INTO `area_info` VALUES ('1214', '350724', '松溪县', '124', '13');
INSERT INTO `area_info` VALUES ('1400', '370681', '龙口市', '143', '15');
INSERT INTO `area_info` VALUES ('2527', '522628', '锦屏县', '269', '24');
INSERT INTO `area_info` VALUES ('619', '220625', '江源县', '58', '7');
INSERT INTO `area_info` VALUES ('2786', '610327', '陇　县', '296', '27');
INSERT INTO `area_info` VALUES ('776', '231226', '绥棱县', '73', '8');
INSERT INTO `area_info` VALUES ('2130', '450701', '市辖区', '227', '20');
INSERT INTO `area_info` VALUES ('233', '140203', '矿　区', '17', '4');
INSERT INTO `area_info` VALUES ('1343', '361127', '余干县', '137', '14');
INSERT INTO `area_info` VALUES ('981', '330702', '婺城区', '96', '11');
INSERT INTO `area_info` VALUES ('1218', '350783', '建瓯市', '124', '13');
INSERT INTO `area_info` VALUES ('1383', '370403', '薛城区', '141', '15');
INSERT INTO `area_info` VALUES ('2763', '610111', '灞桥区', '294', '27');
INSERT INTO `area_info` VALUES ('2708', '542224', '桑日县', '289', '26');
INSERT INTO `area_info` VALUES ('1832', '430481', '耒阳市', '189', '18');
INSERT INTO `area_info` VALUES ('1589', '410811', '山阳区', '162', '16');
INSERT INTO `area_info` VALUES ('2452', '513433', '冕宁县', '261', '23');
INSERT INTO `area_info` VALUES ('262', '140481', '潞城市', '19', '4');
INSERT INTO `area_info` VALUES ('616', '220621', '抚松县', '58', '7');
INSERT INTO `area_info` VALUES ('1424', '370827', '鱼台县', '145', '15');
INSERT INTO `area_info` VALUES ('3092', '652929', '柯坪县', '338', '31');
INSERT INTO `area_info` VALUES ('2242', '500231', '垫江县', '239', '22');
INSERT INTO `area_info` VALUES ('2358', '511401', '市辖区', '252', '23');
INSERT INTO `area_info` VALUES ('1482', '371523', '茌平县', '152', '15');
INSERT INTO `area_info` VALUES ('100', '130433', '馆陶县', '8', '3');
INSERT INTO `area_info` VALUES ('821', '320206', '惠山区', '78', '10');
INSERT INTO `area_info` VALUES ('1918', '431321', '双峰县', '198', '18');
INSERT INTO `area_info` VALUES ('351', '150102', '新城区', '27', '5');
INSERT INTO `area_info` VALUES ('2447', '513428', '普格县', '261', '23');
INSERT INTO `area_info` VALUES ('14', '110115', '大兴区', '365', '1');
INSERT INTO `area_info` VALUES ('2162', '451102', '八步区', '231', '20');
INSERT INTO `area_info` VALUES ('340', '141123', '兴　县', '26', '4');
INSERT INTO `area_info` VALUES ('2485', '520381', '赤水市', '264', '24');
INSERT INTO `area_info` VALUES ('3022', '640121', '永宁县', '326', '30');
INSERT INTO `area_info` VALUES ('1677', '411726', '泌阳县', '171', '16');
INSERT INTO `area_info` VALUES ('2269', '510122', '双流县', '241', '23');
INSERT INTO `area_info` VALUES ('834', '320324', '睢宁县', '79', '10');
INSERT INTO `area_info` VALUES ('192', '130930', '孟村回族自治县', '13', '3');
INSERT INTO `area_info` VALUES ('270', '140601', '市辖区', '21', '4');
INSERT INTO `area_info` VALUES ('2657', '532628', '富宁县', '281', '25');
INSERT INTO `area_info` VALUES ('2121', '450502', '海城区', '225', '20');
INSERT INTO `area_info` VALUES ('3045', '650102', '天山区', '331', '31');
INSERT INTO `area_info` VALUES ('2846', '610821', '神木县', '301', '27');
INSERT INTO `area_info` VALUES ('2415', '513227', '小金县', '259', '23');
INSERT INTO `area_info` VALUES ('1290', '360730', '宁都县', '133', '14');
INSERT INTO `area_info` VALUES ('2247', '500236', '奉节县', '239', '22');
INSERT INTO `area_info` VALUES ('2856', '610831', '子洲县', '301', '27');
INSERT INTO `area_info` VALUES ('1072', '340822', '怀宁县', '108', '12');
INSERT INTO `area_info` VALUES ('902', '321102', '京口区', '87', '10');
INSERT INTO `area_info` VALUES ('958', '330382', '乐清市', '92', '11');
INSERT INTO `area_info` VALUES ('880', '320829', '洪泽县', '84', '10');
INSERT INTO `area_info` VALUES ('554', '211223', '西丰县', '50', '6');
INSERT INTO `area_info` VALUES ('2333', '511024', '威远县', '249', '23');
INSERT INTO `area_info` VALUES ('2037', '441623', '连平县', '213', '19');
INSERT INTO `area_info` VALUES ('2258', '510101', '市辖区', '241', '23');
INSERT INTO `area_info` VALUES ('123', '130582', '沙河市', '9', '3');
INSERT INTO `area_info` VALUES ('2363', '511424', '丹棱县', '252', '23');
INSERT INTO `area_info` VALUES ('2637', '532501', '个旧市', '280', '25');
INSERT INTO `area_info` VALUES ('1809', '430211', '天元区', '187', '18');
INSERT INTO `area_info` VALUES ('2507', '522324', '晴隆县', '267', '24');
INSERT INTO `area_info` VALUES ('1895', '431122', '东安县', '196', '18');
INSERT INTO `area_info` VALUES ('1423', '370826', '微山县', '145', '15');
INSERT INTO `area_info` VALUES ('1630', '411324', '镇平县', '167', '16');
INSERT INTO `area_info` VALUES ('2832', '610701', '市辖区', '300', '27');
INSERT INTO `area_info` VALUES ('2740', '542426', '申扎县', '291', '26');
INSERT INTO `area_info` VALUES ('1376', '370305', '临淄区', '140', '15');
INSERT INTO `area_info` VALUES ('1874', '430903', '赫山区', '194', '18');
INSERT INTO `area_info` VALUES ('471', '210124', '法库县', '39', '6');
INSERT INTO `area_info` VALUES ('2470', '520221', '水城县', '263', '24');
INSERT INTO `area_info` VALUES ('2083', '450201', '市辖区', '222', '20');
INSERT INTO `area_info` VALUES ('921', '330101', '市辖区', '90', '11');
INSERT INTO `area_info` VALUES ('96', '130429', '永年县', '8', '3');
INSERT INTO `area_info` VALUES ('1390', '370503', '河口区', '142', '15');
INSERT INTO `area_info` VALUES ('121', '130535', '临西县', '9', '3');
INSERT INTO `area_info` VALUES ('318', '141001', '市辖区', '25', '4');
INSERT INTO `area_info` VALUES ('2605', '530723', '华坪县', '276', '25');
INSERT INTO `area_info` VALUES ('2307', '510703', '涪城区', '246', '23');
INSERT INTO `area_info` VALUES ('2581', '530427', '新平彝族傣族自治县', '273', '25');
INSERT INTO `area_info` VALUES ('1658', '411528', '息　县', '169', '16');
INSERT INTO `area_info` VALUES ('241', '140226', '左云县', '17', '4');
INSERT INTO `area_info` VALUES ('3039', '640425', '彭阳县', '329', '30');
INSERT INTO `area_info` VALUES ('3063', '652223', '伊吾县', '334', '31');
INSERT INTO `area_info` VALUES ('642', '230102', '道里区', '62', '8');
INSERT INTO `area_info` VALUES ('624', '220722', '长岭县', '59', '7');
INSERT INTO `area_info` VALUES ('706', '230601', '市辖区', '67', '8');
INSERT INTO `area_info` VALUES ('1956', '440304', '福田区', '202', '19');
INSERT INTO `area_info` VALUES ('1609', '411081', '禹州市', '164', '16');
INSERT INTO `area_info` VALUES ('3055', '650203', '克拉玛依区', '332', '31');
INSERT INTO `area_info` VALUES ('3167', '', '黄大仙区', '373', '33');
INSERT INTO `area_info` VALUES ('929', '330110', '余杭区', '90', '11');
INSERT INTO `area_info` VALUES ('2434', '513333', '色达县', '260', '23');
INSERT INTO `area_info` VALUES ('2995', '632324', '河南蒙古族自治县', '321', '29');
INSERT INTO `area_info` VALUES ('1151', '350122', '连江县', '118', '13');
INSERT INTO `area_info` VALUES ('623', '220721', '前郭尔罗斯蒙古族自治县', '59', '7');
INSERT INTO `area_info` VALUES ('1597', '410901', '市辖区', '163', '16');
INSERT INTO `area_info` VALUES ('3152', '453100', '北山区', '355', '39');
INSERT INTO `area_info` VALUES ('1601', '410926', '范　县', '163', '16');
INSERT INTO `area_info` VALUES ('1884', '431023', '永兴县', '195', '18');
INSERT INTO `area_info` VALUES ('1166', '350302', '城厢区', '120', '13');
INSERT INTO `area_info` VALUES ('1320', '360981', '丰城市', '135', '14');
INSERT INTO `area_info` VALUES ('2150', '451021', '田阳县', '230', '20');
INSERT INTO `area_info` VALUES ('822', '320211', '滨湖区', '78', '10');
INSERT INTO `area_info` VALUES ('312', '140928', '五寨县', '24', '4');
INSERT INTO `area_info` VALUES ('1996', '440883', '吴川市', '207', '19');
INSERT INTO `area_info` VALUES ('2335', '511028', '隆昌县', '249', '23');
INSERT INTO `area_info` VALUES ('1061', '340604', '烈山区', '106', '12');
INSERT INTO `area_info` VALUES ('2033', '441601', '市辖区', '213', '19');
INSERT INTO `area_info` VALUES ('3027', '640205', '惠农区', '327', '30');
INSERT INTO `area_info` VALUES ('1951', '440233', '新丰县', '201', '19');
INSERT INTO `area_info` VALUES ('1194', '350581', '石狮市', '122', '13');
INSERT INTO `area_info` VALUES ('279', '140721', '榆社县', '368', '4');
INSERT INTO `area_info` VALUES ('1421', '370802', '市中区', '145', '15');
INSERT INTO `area_info` VALUES ('595', '220302', '铁西区', '55', '7');
INSERT INTO `area_info` VALUES ('602', '220402', '龙山区', '56', '7');
INSERT INTO `area_info` VALUES ('1524', '410221', '杞　县', '156', '16');
INSERT INTO `area_info` VALUES ('3009', '632723', '称多县', '324', '29');
INSERT INTO `area_info` VALUES ('1133', '341721', '东至县', '116', '12');
INSERT INTO `area_info` VALUES ('441', '152222', '科尔沁右翼中旗', '36', '5');
INSERT INTO `area_info` VALUES ('1962', '440402', '香洲区', '203', '19');
INSERT INTO `area_info` VALUES ('38', '130102', '长安区', '5', '3');
INSERT INTO `area_info` VALUES ('2211', '469033', '乐东黎族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('2994', '632323', '泽库县', '321', '29');
INSERT INTO `area_info` VALUES ('2245', '500234', '开　县', '239', '22');
INSERT INTO `area_info` VALUES ('662', '230202', '龙沙区', '63', '8');
INSERT INTO `area_info` VALUES ('566', '211401', '市辖区', '52', '6');
INSERT INTO `area_info` VALUES ('831', '320321', '丰　县', '79', '10');
INSERT INTO `area_info` VALUES ('1092', '341124', '全椒县', '110', '12');
INSERT INTO `area_info` VALUES ('2789', '610330', '凤　县', '296', '27');
INSERT INTO `area_info` VALUES ('564', '211381', '北票市', '51', '6');
INSERT INTO `area_info` VALUES ('503', '210504', '明山区', '43', '6');
INSERT INTO `area_info` VALUES ('406', '150721', '阿荣旗', '33', '5');
INSERT INTO `area_info` VALUES ('1933', '440104', '越秀区', '200', '19');
INSERT INTO `area_info` VALUES ('814', '320124', '溧水县', '77', '10');
INSERT INTO `area_info` VALUES ('1175', '350423', '清流县', '121', '13');
INSERT INTO `area_info` VALUES ('1935', '440106', '天河区', '200', '19');
INSERT INTO `area_info` VALUES ('1656', '411526', '潢川县', '169', '16');
INSERT INTO `area_info` VALUES ('3059', '652122', '鄯善县', '333', '31');
INSERT INTO `area_info` VALUES ('2550', '530103', '盘龙区', '271', '25');
INSERT INTO `area_info` VALUES ('248', '140322', '盂　县', '18', '4');
INSERT INTO `area_info` VALUES ('2619', '530902', '临翔区', '278', '25');
INSERT INTO `area_info` VALUES ('2017', '441323', '惠东县', '210', '19');
INSERT INTO `area_info` VALUES ('2451', '513432', '喜德县', '261', '23');
INSERT INTO `area_info` VALUES ('207', '131082', '三河市', '14', '3');
INSERT INTO `area_info` VALUES ('3018', '640101', '市辖区', '326', '30');
INSERT INTO `area_info` VALUES ('1911', '431227', '新晃侗族自治县', '197', '18');
INSERT INTO `area_info` VALUES ('2915', '620801', '市辖区', '311', '28');
INSERT INTO `area_info` VALUES ('1393', '370523', '广饶县', '142', '15');
INSERT INTO `area_info` VALUES ('643', '230103', '南岗区', '62', '8');
INSERT INTO `area_info` VALUES ('876', '320803', '楚州区', '84', '10');
INSERT INTO `area_info` VALUES ('2312', '510725', '梓潼县', '246', '23');
INSERT INTO `area_info` VALUES ('905', '321181', '丹阳市', '87', '10');
INSERT INTO `area_info` VALUES ('2344', '511129', '沐川县', '250', '23');
INSERT INTO `area_info` VALUES ('2952', '621223', '宕昌县', '315', '28');
INSERT INTO `area_info` VALUES ('157', '130723', '康保县', '11', '3');
INSERT INTO `area_info` VALUES ('841', '320411', '新北区', '80', '10');
INSERT INTO `area_info` VALUES ('1', '110101', '东城区', '365', '1');
INSERT INTO `area_info` VALUES ('3012', '632726', '曲麻莱县', '324', '29');
INSERT INTO `area_info` VALUES ('2572', '530381', '宣威市', '272', '25');
INSERT INTO `area_info` VALUES ('1178', '350426', '尤溪县', '121', '13');
INSERT INTO `area_info` VALUES ('2882', '620121', '永登县', '304', '28');
INSERT INTO `area_info` VALUES ('954', '330327', '苍南县', '92', '11');
INSERT INTO `area_info` VALUES ('1723', '420582', '当阳市', '175', '17');
INSERT INTO `area_info` VALUES ('1713', '420503', '伍家岗区', '175', '17');
INSERT INTO `area_info` VALUES ('1260', '360402', '庐山区', '130', '14');
INSERT INTO `area_info` VALUES ('987', '330782', '义乌市', '96', '11');
INSERT INTO `area_info` VALUES ('1799', '430105', '开福区', '186', '18');
INSERT INTO `area_info` VALUES ('962', '330421', '嘉善县', '93', '11');
INSERT INTO `area_info` VALUES ('110', '130524', '柏乡县', '9', '3');
INSERT INTO `area_info` VALUES ('2399', '511901', '市辖区', '257', '23');
INSERT INTO `area_info` VALUES ('97', '130430', '邱　县', '8', '3');
INSERT INTO `area_info` VALUES ('1770', '421127', '黄梅县', '181', '17');
INSERT INTO `area_info` VALUES ('618', '220623', '长白朝鲜族自治县', '58', '7');
INSERT INTO `area_info` VALUES ('2216', '469038', '南沙群岛', '237', '21');
INSERT INTO `area_info` VALUES ('469', '210122', '辽中县', '39', '6');
INSERT INTO `area_info` VALUES ('1793', '429006', '天门市', '185', '17');
INSERT INTO `area_info` VALUES ('2144', '450922', '陆川县', '229', '20');
INSERT INTO `area_info` VALUES ('2325', '510903', '船山区', '248', '23');
INSERT INTO `area_info` VALUES ('2726', '542330', '仁布县', '290', '26');
INSERT INTO `area_info` VALUES ('1553', '410425', '郏　县', '158', '16');
INSERT INTO `area_info` VALUES ('2500', '522227', '德江县', '266', '24');
INSERT INTO `area_info` VALUES ('347', '141130', '交口县', '26', '4');
INSERT INTO `area_info` VALUES ('468', '210114', '于洪区', '39', '6');
INSERT INTO `area_info` VALUES ('484', '210301', '市辖区', '41', '6');
INSERT INTO `area_info` VALUES ('3111', '653222', '墨玉县', '341', '31');
INSERT INTO `area_info` VALUES ('2728', '542332', '定结县', '290', '26');
INSERT INTO `area_info` VALUES ('1899', '431126', '宁远县', '196', '18');
INSERT INTO `area_info` VALUES ('23', '120105', '河北区', '366', '2');
INSERT INTO `area_info` VALUES ('696', '230422', '绥滨县', '65', '8');
INSERT INTO `area_info` VALUES ('603', '220403', '西安区', '56', '7');
INSERT INTO `area_info` VALUES ('1234', '350925', '周宁县', '126', '13');
INSERT INTO `area_info` VALUES ('1065', '340703', '狮子山区', '107', '12');
INSERT INTO `area_info` VALUES ('604', '220421', '东丰县', '56', '7');
INSERT INTO `area_info` VALUES ('2707', '542223', '贡嘎县', '289', '26');
INSERT INTO `area_info` VALUES ('3004', '632624', '达日县', '323', '29');
INSERT INTO `area_info` VALUES ('2927', '620923', '肃北蒙古族自治县', '312', '28');
INSERT INTO `area_info` VALUES ('2126', '450602', '港口区', '226', '20');
INSERT INTO `area_info` VALUES ('2429', '513328', '甘孜县', '260', '23');
INSERT INTO `area_info` VALUES ('2009', '441225', '封开县', '209', '19');
INSERT INTO `area_info` VALUES ('2351', '511304', '嘉陵区', '251', '23');
INSERT INTO `area_info` VALUES ('1663', '411623', '商水县', '170', '16');
INSERT INTO `area_info` VALUES ('2431', '513330', '德格县', '260', '23');
INSERT INTO `area_info` VALUES ('1328', '361024', '崇仁县', '136', '14');
INSERT INTO `area_info` VALUES ('713', '230622', '肇源县', '67', '8');
INSERT INTO `area_info` VALUES ('2720', '542324', '定日县', '290', '26');
INSERT INTO `area_info` VALUES ('3040', '640501', '市辖区', '330', '30');
INSERT INTO `area_info` VALUES ('646', '230107', '动力区', '62', '8');
INSERT INTO `area_info` VALUES ('795', '310115', '浦东新区', '367', '9');
INSERT INTO `area_info` VALUES ('2905', '620621', '民勤县', '309', '28');
INSERT INTO `area_info` VALUES ('3023', '640122', '贺兰县', '326', '30');
INSERT INTO `area_info` VALUES ('2388', '511725', '渠　县', '255', '23');
INSERT INTO `area_info` VALUES ('2724', '542328', '谢通门县', '290', '26');
INSERT INTO `area_info` VALUES ('155', '130721', '宣化县', '11', '3');
INSERT INTO `area_info` VALUES ('3086', '652923', '库车县', '338', '31');
INSERT INTO `area_info` VALUES ('1579', '410725', '原阳县', '161', '16');
INSERT INTO `area_info` VALUES ('2566', '530322', '陆良县', '272', '25');
INSERT INTO `area_info` VALUES ('1135', '341723', '青阳县', '116', '12');
INSERT INTO `area_info` VALUES ('2340', '511113', '金口河区', '250', '23');
INSERT INTO `area_info` VALUES ('1351', '370104', '槐荫区', '138', '15');
INSERT INTO `area_info` VALUES ('1550', '410421', '宝丰县', '158', '16');
INSERT INTO `area_info` VALUES ('1471', '371424', '临邑县', '151', '15');
INSERT INTO `area_info` VALUES ('746', '230901', '市辖区', '70', '8');
INSERT INTO `area_info` VALUES ('1099', '341203', '颍东区', '111', '12');
INSERT INTO `area_info` VALUES ('755', '231005', '西安区', '71', '8');
INSERT INTO `area_info` VALUES ('1210', '350702', '延平区', '124', '13');
INSERT INTO `area_info` VALUES ('1791', '429004', '仙桃市', '185', '17');
INSERT INTO `area_info` VALUES ('2666', '532926', '南涧彝族自治县', '283', '25');
INSERT INTO `area_info` VALUES ('2914', '620725', '山丹县', '310', '28');
INSERT INTO `area_info` VALUES ('1463', '371327', '莒南县', '150', '15');
INSERT INTO `area_info` VALUES ('600', '220382', '双辽市', '55', '7');
INSERT INTO `area_info` VALUES ('1917', '431302', '娄星区', '198', '18');
INSERT INTO `area_info` VALUES ('858', '320602', '崇川区', '82', '10');
INSERT INTO `area_info` VALUES ('1777', '421223', '崇阳县', '182', '17');
INSERT INTO `area_info` VALUES ('634', '222402', '图们市', '61', '7');
INSERT INTO `area_info` VALUES ('1141', '341824', '绩溪县', '117', '12');
INSERT INTO `area_info` VALUES ('1700', '420222', '阳新县', '173', '17');
INSERT INTO `area_info` VALUES ('524', '210803', '西市区', '46', '6');
INSERT INTO `area_info` VALUES ('3145', '3501001', '鼓楼区', '346', '37');
INSERT INTO `area_info` VALUES ('244', '140302', '城　区', '18', '4');
INSERT INTO `area_info` VALUES ('2991', '632224', '刚察县', '320', '29');
INSERT INTO `area_info` VALUES ('1685', '420105', '汉阳区', '172', '17');
INSERT INTO `area_info` VALUES ('1652', '411522', '光山县', '169', '16');
INSERT INTO `area_info` VALUES ('175', '130824', '滦平县', '12', '3');
INSERT INTO `area_info` VALUES ('2020', '441402', '梅江区', '211', '19');
INSERT INTO `area_info` VALUES ('2178', '451301', '市辖区', '233', '20');
INSERT INTO `area_info` VALUES ('2188', '451422', '宁明县', '234', '20');
INSERT INTO `area_info` VALUES ('1970', '440514', '潮南区', '204', '19');
INSERT INTO `area_info` VALUES ('222', '140106', '迎泽区', '16', '4');
INSERT INTO `area_info` VALUES ('2420', '513232', '若尔盖县', '259', '23');
INSERT INTO `area_info` VALUES ('1893', '431103', '冷水滩区', '196', '18');
INSERT INTO `area_info` VALUES ('419', '150802', '临河区', '34', '5');
INSERT INTO `area_info` VALUES ('2971', '623025', '玛曲县', '317', '28');
INSERT INTO `area_info` VALUES ('2249', '500238', '巫溪县', '239', '22');
INSERT INTO `area_info` VALUES ('3078', '652824', '若羌县', '337', '31');
INSERT INTO `area_info` VALUES ('2208', '469028', '临高县', '237', '21');
INSERT INTO `area_info` VALUES ('431', '150924', '兴和县', '35', '5');
INSERT INTO `area_info` VALUES ('323', '141024', '洪洞县', '25', '4');
INSERT INTO `area_info` VALUES ('1363', '370211', '黄岛区', '139', '15');
INSERT INTO `area_info` VALUES ('2285', '510401', '市辖区', '243', '23');
INSERT INTO `area_info` VALUES ('1013', '331102', '莲都区', '100', '11');
INSERT INTO `area_info` VALUES ('371', '150302', '海勃湾区', '29', '5');
INSERT INTO `area_info` VALUES ('2649', '532532', '河口瑶族自治县', '280', '25');
INSERT INTO `area_info` VALUES ('757', '231025', '林口县', '71', '8');
INSERT INTO `area_info` VALUES ('2124', '450521', '合浦县', '225', '20');
INSERT INTO `area_info` VALUES ('1094', '341126', '凤阳县', '110', '12');
INSERT INTO `area_info` VALUES ('191', '130929', '献　县', '13', '3');
INSERT INTO `area_info` VALUES ('2772', '610126', '高陵县', '294', '27');
INSERT INTO `area_info` VALUES ('1859', '430703', '鼎城区', '192', '18');
INSERT INTO `area_info` VALUES ('1016', '331123', '遂昌县', '100', '11');
INSERT INTO `area_info` VALUES ('2706', '542222', '扎囊县', '289', '26');
INSERT INTO `area_info` VALUES ('1036', '340222', '繁昌县', '102', '12');
INSERT INTO `area_info` VALUES ('606', '220501', '市辖区', '57', '7');
INSERT INTO `area_info` VALUES ('1012', '331101', '市辖区', '100', '11');
INSERT INTO `area_info` VALUES ('2088', '450221', '柳江县', '222', '20');
INSERT INTO `area_info` VALUES ('370', '150301', '市辖区', '29', '5');
INSERT INTO `area_info` VALUES ('2815', '610528', '富平县', '298', '27');
INSERT INTO `area_info` VALUES ('1324', '361002', '临川区', '136', '14');
INSERT INTO `area_info` VALUES ('3070', '652327', '吉木萨尔县', '335', '31');
INSERT INTO `area_info` VALUES ('1880', '431002', '北湖区', '195', '18');
INSERT INTO `area_info` VALUES ('2450', '513431', '昭觉县', '261', '23');
INSERT INTO `area_info` VALUES ('2816', '610581', '韩城市', '298', '27');
INSERT INTO `area_info` VALUES ('1644', '411424', '柘城县', '168', '16');
INSERT INTO `area_info` VALUES ('2075', '450108', '良庆区', '221', '20');
INSERT INTO `area_info` VALUES ('2222', '500105', '江北区', '238', '22');
INSERT INTO `area_info` VALUES ('1872', '430901', '市辖区', '194', '18');
INSERT INTO `area_info` VALUES ('1470', '371423', '庆云县', '151', '15');
INSERT INTO `area_info` VALUES ('2762', '610104', '莲湖区', '294', '27');
INSERT INTO `area_info` VALUES ('514', '210701', '市辖区', '45', '6');
INSERT INTO `area_info` VALUES ('2154', '451025', '靖西县', '230', '20');
INSERT INTO `area_info` VALUES ('299', '140829', '平陆县', '23', '4');
INSERT INTO `area_info` VALUES ('1982', '440705', '新会区', '206', '19');
INSERT INTO `area_info` VALUES ('108', '130522', '临城县', '9', '3');
INSERT INTO `area_info` VALUES ('1395', '370602', '芝罘区', '143', '15');
INSERT INTO `area_info` VALUES ('443', '152224', '突泉县', '36', '5');
INSERT INTO `area_info` VALUES ('346', '141129', '中阳县', '26', '4');
INSERT INTO `area_info` VALUES ('1578', '410724', '获嘉县', '161', '16');
INSERT INTO `area_info` VALUES ('2685', '540101', '市辖区', '287', '26');
INSERT INTO `area_info` VALUES ('8', '110108', '海淀区', '365', '1');
INSERT INTO `area_info` VALUES ('2219', '500102', '涪陵区', '238', '22');
INSERT INTO `area_info` VALUES ('1484', '371525', '冠　县', '152', '15');
INSERT INTO `area_info` VALUES ('3087', '652924', '沙雅县', '338', '31');
INSERT INTO `area_info` VALUES ('2871', '611022', '丹凤县', '303', '27');
INSERT INTO `area_info` VALUES ('1906', '431222', '沅陵县', '197', '18');
INSERT INTO `area_info` VALUES ('214', '131125', '安平县', '15', '3');
INSERT INTO `area_info` VALUES ('498', '210422', '新宾满族自治县', '42', '6');
INSERT INTO `area_info` VALUES ('1989', '440803', '霞山区', '207', '19');
INSERT INTO `area_info` VALUES ('2495', '522222', '江口县', '266', '24');
INSERT INTO `area_info` VALUES ('2567', '530323', '师宗县', '272', '25');
INSERT INTO `area_info` VALUES ('2555', '530122', '晋宁县', '271', '25');
INSERT INTO `area_info` VALUES ('3141', '659001', '石河子市', '345', '31');
INSERT INTO `area_info` VALUES ('808', '320107', '下关区', '77', '10');
INSERT INTO `area_info` VALUES ('1464', '371328', '蒙阴县', '150', '15');
INSERT INTO `area_info` VALUES ('2268', '510121', '金堂县', '241', '23');
INSERT INTO `area_info` VALUES ('3031', '640323', '盐池县', '328', '30');
INSERT INTO `area_info` VALUES ('2184', '451381', '合山市', '233', '20');
INSERT INTO `area_info` VALUES ('629', '220821', '镇赉县', '60', '7');
INSERT INTO `area_info` VALUES ('824', '320282', '宜兴市', '78', '10');
INSERT INTO `area_info` VALUES ('1980', '440703', '蓬江区', '206', '19');
INSERT INTO `area_info` VALUES ('622', '220702', '宁江区', '59', '7');
INSERT INTO `area_info` VALUES ('1547', '410403', '卫东区', '158', '16');
INSERT INTO `area_info` VALUES ('2391', '511802', '雨城区', '256', '23');
INSERT INTO `area_info` VALUES ('2213', '469035', '保亭黎族苗族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('1701', '420281', '大冶市', '173', '17');
INSERT INTO `area_info` VALUES ('1414', '370781', '青州市', '144', '15');
INSERT INTO `area_info` VALUES ('1142', '341825', '旌德县', '117', '12');
INSERT INTO `area_info` VALUES ('1353', '370112', '历城区', '138', '15');
INSERT INTO `area_info` VALUES ('2780', '610303', '金台区', '296', '27');
INSERT INTO `area_info` VALUES ('2939', '621027', '镇原县', '313', '28');
INSERT INTO `area_info` VALUES ('3005', '632625', '久治县', '323', '29');
INSERT INTO `area_info` VALUES ('363', '150204', '青山区', '28', '5');
INSERT INTO `area_info` VALUES ('589', '220221', '永吉县', '54', '7');
INSERT INTO `area_info` VALUES ('1804', '430181', '浏阳市', '186', '18');
INSERT INTO `area_info` VALUES ('1897', '431124', '道　县', '196', '18');
INSERT INTO `area_info` VALUES ('1322', '360983', '高安市', '135', '14');
INSERT INTO `area_info` VALUES ('78', '130303', '山海关区', '7', '3');
INSERT INTO `area_info` VALUES ('1456', '371312', '河东区', '150', '15');
INSERT INTO `area_info` VALUES ('767', '231181', '北安市', '72', '8');
INSERT INTO `area_info` VALUES ('1671', '411702', '驿城区', '171', '16');
INSERT INTO `area_info` VALUES ('2232', '500115', '长寿区', '238', '22');
INSERT INTO `area_info` VALUES ('1888', '431027', '桂东县', '195', '18');
INSERT INTO `area_info` VALUES ('1257', '360322', '上栗县', '129', '14');
INSERT INTO `area_info` VALUES ('37', '130101', '市辖区', '5', '3');
INSERT INTO `area_info` VALUES ('2824', '610625', '志丹县', '299', '27');
INSERT INTO `area_info` VALUES ('2436', '513335', '巴塘县', '260', '23');
INSERT INTO `area_info` VALUES ('2736', '542422', '嘉黎县', '291', '26');
INSERT INTO `area_info` VALUES ('310', '140926', '静乐县', '24', '4');
INSERT INTO `area_info` VALUES ('3112', '653223', '皮山县', '341', '31');
INSERT INTO `area_info` VALUES ('2896', '620502', '秦城区', '308', '28');
INSERT INTO `area_info` VALUES ('1503', '371727', '定陶县', '154', '15');
INSERT INTO `area_info` VALUES ('1043', '340321', '怀远县', '103', '12');
INSERT INTO `area_info` VALUES ('758', '231081', '绥芬河市', '71', '8');
INSERT INTO `area_info` VALUES ('1259', '360401', '市辖区', '130', '14');
INSERT INTO `area_info` VALUES ('2982', '632121', '平安县', '319', '29');
INSERT INTO `area_info` VALUES ('815', '320125', '高淳县', '77', '10');
INSERT INTO `area_info` VALUES ('1426', '370829', '嘉祥县', '145', '15');
INSERT INTO `area_info` VALUES ('1406', '370687', '海阳市', '143', '15');
INSERT INTO `area_info` VALUES ('769', '231201', '市辖区', '73', '8');
INSERT INTO `area_info` VALUES ('465', '210111', '苏家屯区', '39', '6');
INSERT INTO `area_info` VALUES ('1254', '360302', '安源区', '129', '14');
INSERT INTO `area_info` VALUES ('164', '130730', '怀来县', '11', '3');
INSERT INTO `area_info` VALUES ('2059', '445202', '榕城区', '219', '19');
INSERT INTO `area_info` VALUES ('2693', '540127', '墨竹工卡县', '287', '26');
INSERT INTO `area_info` VALUES ('1225', '350824', '武平县', '125', '13');
INSERT INTO `area_info` VALUES ('855', '320584', '吴江市', '81', '10');
INSERT INTO `area_info` VALUES ('1811', '430223', '攸　县', '187', '18');
INSERT INTO `area_info` VALUES ('708', '230603', '龙凤区', '67', '8');
INSERT INTO `area_info` VALUES ('309', '140925', '宁武县', '24', '4');
INSERT INTO `area_info` VALUES ('1806', '430202', '荷塘区', '187', '18');
INSERT INTO `area_info` VALUES ('2417', '513229', '马尔康县', '259', '23');
INSERT INTO `area_info` VALUES ('1821', '430401', '市辖区', '189', '18');
INSERT INTO `area_info` VALUES ('3140', '654326', '吉木乃县', '344', '31');
INSERT INTO `area_info` VALUES ('1673', '411722', '上蔡县', '171', '16');
INSERT INTO `area_info` VALUES ('122', '130581', '南宫市', '9', '3');
INSERT INTO `area_info` VALUES ('1359', '370201', '市辖区', '139', '15');
INSERT INTO `area_info` VALUES ('2052', '441881', '英德市', '215', '19');
INSERT INTO `area_info` VALUES ('221', '140105', '小店区', '16', '4');
INSERT INTO `area_info` VALUES ('969', '330503', '南浔区', '94', '11');
INSERT INTO `area_info` VALUES ('402', '150626', '乌审旗', '32', '5');
INSERT INTO `area_info` VALUES ('2902', '620525', '张家川回族自治县', '308', '28');
INSERT INTO `area_info` VALUES ('273', '140621', '山阴县', '21', '4');
INSERT INTO `area_info` VALUES ('2304', '510682', '什邡市', '245', '23');
INSERT INTO `area_info` VALUES ('1556', '410501', '市辖区', '159', '16');
INSERT INTO `area_info` VALUES ('1546', '410402', '新华区', '158', '16');
INSERT INTO `area_info` VALUES ('1389', '370502', '东营区', '142', '15');
INSERT INTO `area_info` VALUES ('1233', '350924', '寿宁县', '126', '13');
INSERT INTO `area_info` VALUES ('2172', '451225', '罗城仫佬族自治县', '232', '20');
INSERT INTO `area_info` VALUES ('474', '210202', '中山区', '40', '6');
INSERT INTO `area_info` VALUES ('193', '130981', '泊头市', '13', '3');
INSERT INTO `area_info` VALUES ('1728', '420607', '襄阳区', '176', '17');
INSERT INTO `area_info` VALUES ('2969', '623023', '舟曲县', '317', '28');
INSERT INTO `area_info` VALUES ('1570', '410621', '浚　县', '160', '16');
INSERT INTO `area_info` VALUES ('3189', '330922', '市辖区', '383', '17');
INSERT INTO `area_info` VALUES ('726', '230711', '乌马河区', '68', '8');
INSERT INTO `area_info` VALUES ('1022', '340101', '市辖区', '101', '12');
INSERT INTO `area_info` VALUES ('1805', '430201', '市辖区', '187', '18');
INSERT INTO `area_info` VALUES ('2155', '451026', '那坡县', '230', '20');
INSERT INTO `area_info` VALUES ('2681', '533325', '兰坪白族普米族自治县', '285', '25');
INSERT INTO `area_info` VALUES ('3068', '652324', '玛纳斯县', '335', '31');
INSERT INTO `area_info` VALUES ('1276', '360602', '月湖区', '132', '14');
INSERT INTO `area_info` VALUES ('3126', '654028', '尼勒克县', '342', '31');
INSERT INTO `area_info` VALUES ('197', '131001', '市辖区', '14', '3');
INSERT INTO `area_info` VALUES ('1534', '410306', '吉利区', '157', '16');
INSERT INTO `area_info` VALUES ('2283', '510321', '荣　县', '242', '23');
INSERT INTO `area_info` VALUES ('1358', '370181', '章丘市', '138', '15');
INSERT INTO `area_info` VALUES ('2359', '511402', '东坡区', '252', '23');
INSERT INTO `area_info` VALUES ('1365', '370213', '李沧区', '139', '15');
INSERT INTO `area_info` VALUES ('1782', '421381', '广水市', '183', '17');
INSERT INTO `area_info` VALUES ('2043', '441723', '阳东县', '214', '19');
INSERT INTO `area_info` VALUES ('2620', '530921', '凤庆县', '278', '25');
INSERT INTO `area_info` VALUES ('525', '210804', '鲅鱼圈区', '46', '6');
INSERT INTO `area_info` VALUES ('171', '130804', '鹰手营子矿区', '12', '3');
INSERT INTO `area_info` VALUES ('2737', '542423', '比如县', '291', '26');
INSERT INTO `area_info` VALUES ('1004', '331003', '黄岩区', '99', '11');
INSERT INTO `area_info` VALUES ('1298', '360801', '市辖区', '134', '14');
INSERT INTO `area_info` VALUES ('639', '222424', '汪清县', '61', '7');
INSERT INTO `area_info` VALUES ('1354', '370113', '长清区', '138', '15');
INSERT INTO `area_info` VALUES ('1475', '371428', '武城县', '151', '15');
INSERT INTO `area_info` VALUES ('861', '320623', '如东县', '82', '10');
INSERT INTO `area_info` VALUES ('2205', '469025', '定安县', '237', '21');
INSERT INTO `area_info` VALUES ('1573', '410702', '红旗区', '161', '16');
INSERT INTO `area_info` VALUES ('1091', '341122', '来安县', '110', '12');
INSERT INTO `area_info` VALUES ('1018', '331125', '云和县', '100', '11');
INSERT INTO `area_info` VALUES ('517', '210711', '太和区', '45', '6');
INSERT INTO `area_info` VALUES ('505', '210521', '本溪满族自治县', '43', '6');
INSERT INTO `area_info` VALUES ('2589', '530601', '市辖区', '275', '25');
INSERT INTO `area_info` VALUES ('362', '150203', '昆都仑区', '28', '5');
INSERT INTO `area_info` VALUES ('1586', '410802', '解放区', '162', '16');
INSERT INTO `area_info` VALUES ('2385', '511722', '宣汉县', '255', '23');
INSERT INTO `area_info` VALUES ('2389', '511781', '万源市', '255', '23');
INSERT INTO `area_info` VALUES ('1688', '420111', '洪山区', '172', '17');
INSERT INTO `area_info` VALUES ('455', '152531', '多伦县', '37', '5');
INSERT INTO `area_info` VALUES ('854', '320583', '昆山市', '81', '10');
INSERT INTO `area_info` VALUES ('1021', '331181', '龙泉市', '100', '11');
INSERT INTO `area_info` VALUES ('842', '320412', '武进区', '80', '10');
INSERT INTO `area_info` VALUES ('2350', '511303', '高坪区', '251', '23');
INSERT INTO `area_info` VALUES ('802', '320101', '市辖区', '77', '10');
INSERT INTO `area_info` VALUES ('204', '131026', '文安县', '14', '3');
INSERT INTO `area_info` VALUES ('568', '211403', '龙港区', '52', '6');
INSERT INTO `area_info` VALUES ('152', '130703', '桥西区', '11', '3');
INSERT INTO `area_info` VALUES ('256', '140426', '黎城县', '19', '4');
INSERT INTO `area_info` VALUES ('135', '130628', '高阳县', '10', '3');
INSERT INTO `area_info` VALUES ('1029', '340123', '肥西县', '101', '12');
INSERT INTO `area_info` VALUES ('1612', '411102', '源汇区', '165', '16');
INSERT INTO `area_info` VALUES ('2593', '530623', '盐津县', '275', '25');
INSERT INTO `area_info` VALUES ('150', '130701', '市辖区', '11', '3');
INSERT INTO `area_info` VALUES ('1646', '411426', '夏邑县', '168', '16');
INSERT INTO `area_info` VALUES ('1385', '370405', '台儿庄区', '141', '15');
INSERT INTO `area_info` VALUES ('257', '140427', '壶关县', '19', '4');
INSERT INTO `area_info` VALUES ('527', '210881', '盖州市', '46', '6');
INSERT INTO `area_info` VALUES ('2987', '632128', '循化撒拉族自治县', '319', '29');
INSERT INTO `area_info` VALUES ('457', '152922', '阿拉善右旗', '38', '5');
INSERT INTO `area_info` VALUES ('2505', '522322', '兴仁县', '267', '24');
INSERT INTO `area_info` VALUES ('3153', '987110', '南昌县', '356', '52');
INSERT INTO `area_info` VALUES ('817', '320202', '崇安区', '78', '10');
INSERT INTO `area_info` VALUES ('1074', '340824', '潜山县', '108', '12');
INSERT INTO `area_info` VALUES ('1477', '371482', '禹城市', '151', '15');
INSERT INTO `area_info` VALUES ('58', '130183', '晋州市', '5', '3');
INSERT INTO `area_info` VALUES ('1590', '410821', '修武县', '162', '16');
INSERT INTO `area_info` VALUES ('3096', '653024', '乌恰县', '339', '31');
INSERT INTO `area_info` VALUES ('865', '320684', '海门市', '82', '10');
INSERT INTO `area_info` VALUES ('1333', '361029', '东乡县', '136', '14');
INSERT INTO `area_info` VALUES ('816', '320201', '市辖区', '78', '10');
INSERT INTO `area_info` VALUES ('2368', '511522', '南溪县', '253', '23');
INSERT INTO `area_info` VALUES ('2859', '610921', '汉阴县', '302', '27');
INSERT INTO `area_info` VALUES ('3046', '650103', '沙依巴克区', '331', '31');
INSERT INTO `area_info` VALUES ('2884', '620123', '榆中县', '304', '28');
INSERT INTO `area_info` VALUES ('2463', '520114', '小河区', '262', '24');
INSERT INTO `area_info` VALUES ('3185', '522401', '市辖区', '379', '24');
INSERT INTO `area_info` VALUES ('2705', '542221', '乃东县', '289', '26');
INSERT INTO `area_info` VALUES ('888', '320923', '阜宁县', '85', '10');
INSERT INTO `area_info` VALUES ('3043', '640522', '海原县', '330', '30');
INSERT INTO `area_info` VALUES ('302', '140882', '河津市', '23', '4');
INSERT INTO `area_info` VALUES ('2393', '511822', '荥经县', '256', '23');
INSERT INTO `area_info` VALUES ('743', '230833', '抚远县', '69', '8');
INSERT INTO `area_info` VALUES ('1183', '350481', '永安市', '121', '13');
INSERT INTO `area_info` VALUES ('2868', '611001', '市辖区', '303', '27');
INSERT INTO `area_info` VALUES ('143', '130636', '顺平县', '10', '3');
INSERT INTO `area_info` VALUES ('675', '230230', '克东县', '63', '8');
INSERT INTO `area_info` VALUES ('1548', '410404', '石龙区', '158', '16');
INSERT INTO `area_info` VALUES ('2679', '533323', '福贡县', '285', '25');
INSERT INTO `area_info` VALUES ('1526', '410223', '尉氏县', '156', '16');
INSERT INTO `area_info` VALUES ('735', '230802', '永红区', '69', '8');
INSERT INTO `area_info` VALUES ('2152', '451023', '平果县', '230', '20');
INSERT INTO `area_info` VALUES ('489', '210321', '台安县', '41', '6');
INSERT INTO `area_info` VALUES ('2085', '450203', '鱼峰区', '222', '20');
INSERT INTO `area_info` VALUES ('3144', '659004', '五家渠市', '345', '31');
INSERT INTO `area_info` VALUES ('2021', '441421', '梅　县', '211', '19');
INSERT INTO `area_info` VALUES ('76', '130301', '市辖区', '7', '3');
INSERT INTO `area_info` VALUES ('885', '320903', '盐都区', '85', '10');
INSERT INTO `area_info` VALUES ('2460', '520111', '花溪区', '262', '24');
INSERT INTO `area_info` VALUES ('1334', '361030', '广昌县', '136', '14');
INSERT INTO `area_info` VALUES ('2469', '520203', '六枝特区', '263', '24');
INSERT INTO `area_info` VALUES ('3067', '652323', '呼图壁县', '335', '31');
INSERT INTO `area_info` VALUES ('1826', '430412', '南岳区', '189', '18');
INSERT INTO `area_info` VALUES ('1778', '421224', '通山县', '182', '17');
INSERT INTO `area_info` VALUES ('210', '131121', '枣强县', '15', '3');
INSERT INTO `area_info` VALUES ('725', '230710', '五营区', '68', '8');
INSERT INTO `area_info` VALUES ('833', '320323', '铜山县', '79', '10');
INSERT INTO `area_info` VALUES ('2326', '510904', '安居区', '248', '23');
INSERT INTO `area_info` VALUES ('2347', '511181', '峨眉山市', '250', '23');
INSERT INTO `area_info` VALUES ('392', '150524', '库伦旗', '31', '5');
INSERT INTO `area_info` VALUES ('2019', '441401', '市辖区', '211', '19');
INSERT INTO `area_info` VALUES ('1766', '421123', '罗田县', '181', '17');
INSERT INTO `area_info` VALUES ('1299', '360802', '吉州区', '134', '14');
INSERT INTO `area_info` VALUES ('591', '220282', '桦甸市', '54', '7');
INSERT INTO `area_info` VALUES ('1691', '420114', '蔡甸区', '172', '17');
INSERT INTO `area_info` VALUES ('2395', '511824', '石棉县', '256', '23');
INSERT INTO `area_info` VALUES ('1357', '370126', '商河县', '138', '15');
INSERT INTO `area_info` VALUES ('1121', '341521', '寿　县', '114', '12');
INSERT INTO `area_info` VALUES ('344', '141127', '岚　县', '26', '4');
INSERT INTO `area_info` VALUES ('125', '130602', '新市区', '10', '3');
INSERT INTO `area_info` VALUES ('1595', '410882', '沁阳市', '162', '16');
INSERT INTO `area_info` VALUES ('644', '230104', '道外区', '62', '8');
INSERT INTO `area_info` VALUES ('666', '230206', '富拉尔基区', '63', '8');
INSERT INTO `area_info` VALUES ('1420', '370801', '市辖区', '145', '15');
INSERT INTO `area_info` VALUES ('115', '130529', '巨鹿县', '9', '3');
INSERT INTO `area_info` VALUES ('2224', '500107', '九龙坡区', '238', '22');
INSERT INTO `area_info` VALUES ('2579', '530425', '易门县', '273', '25');
INSERT INTO `area_info` VALUES ('2722', '542326', '拉孜县', '290', '26');
INSERT INTO `area_info` VALUES ('2975', '630102', '城东区', '318', '29');
INSERT INTO `area_info` VALUES ('461', '210103', '沈河区', '39', '6');
INSERT INTO `area_info` VALUES ('1808', '430204', '石峰区', '187', '18');
INSERT INTO `area_info` VALUES ('2711', '542227', '措美县', '289', '26');
INSERT INTO `area_info` VALUES ('3042', '640521', '中宁县', '330', '30');
INSERT INTO `area_info` VALUES ('2169', '451222', '天峨县', '232', '20');
INSERT INTO `area_info` VALUES ('730', '230715', '红星区', '68', '8');
INSERT INTO `area_info` VALUES ('2318', '510811', '元坝区', '247', '23');
INSERT INTO `area_info` VALUES ('789', '310108', '闸北区', '367', '9');
INSERT INTO `area_info` VALUES ('890', '320925', '建湖县', '85', '10');
INSERT INTO `area_info` VALUES ('3051', '650108', '东山区', '331', '31');
INSERT INTO `area_info` VALUES ('1028', '340122', '肥东县', '101', '12');
INSERT INTO `area_info` VALUES ('3179', '666666', '市辖区', '369', '32');
INSERT INTO `area_info` VALUES ('857', '320601', '市辖区', '82', '10');
INSERT INTO `area_info` VALUES ('3130', '654223', '沙湾县', '343', '31');
INSERT INTO `area_info` VALUES ('2302', '510626', '罗江县', '245', '23');
INSERT INTO `area_info` VALUES ('2022', '441422', '大埔县', '211', '19');
INSERT INTO `area_info` VALUES ('1232', '350923', '屏南县', '126', '13');
INSERT INTO `area_info` VALUES ('1410', '370704', '坊子区', '144', '15');
INSERT INTO `area_info` VALUES ('183', '130921', '沧　县', '13', '3');
INSERT INTO `area_info` VALUES ('2602', '530702', '古城区', '276', '25');
INSERT INTO `area_info` VALUES ('2376', '511601', '市辖区', '254', '23');
INSERT INTO `area_info` VALUES ('3036', '640422', '西吉县', '329', '30');
INSERT INTO `area_info` VALUES ('2482', '520328', '湄潭县', '264', '24');
INSERT INTO `area_info` VALUES ('1978', '440608', '高明区', '205', '19');
INSERT INTO `area_info` VALUES ('2597', '530627', '镇雄县', '275', '25');
INSERT INTO `area_info` VALUES ('242', '140227', '大同县', '17', '4');
INSERT INTO `area_info` VALUES ('1637', '411381', '邓州市', '167', '16');
INSERT INTO `area_info` VALUES ('2767', '610115', '临潼区', '294', '27');
INSERT INTO `area_info` VALUES ('3142', '659002', '阿拉尔市', '345', '31');
INSERT INTO `area_info` VALUES ('1391', '370521', '垦利县', '142', '15');
INSERT INTO `area_info` VALUES ('2544', '522729', '长顺县', '270', '24');
INSERT INTO `area_info` VALUES ('1179', '350427', '沙　县', '121', '13');
INSERT INTO `area_info` VALUES ('2559', '530127', '嵩明县', '271', '25');
INSERT INTO `area_info` VALUES ('2926', '620922', '安西县', '312', '28');
INSERT INTO `area_info` VALUES ('2135', '450801', '市辖区', '228', '20');
INSERT INTO `area_info` VALUES ('1574', '410703', '卫滨区', '161', '16');
INSERT INTO `area_info` VALUES ('1055', '340503', '花山区', '105', '12');
INSERT INTO `area_info` VALUES ('1375', '370304', '博山区', '140', '15');
INSERT INTO `area_info` VALUES ('1339', '361123', '玉山县', '137', '14');
INSERT INTO `area_info` VALUES ('2082', '450127', '横　县', '221', '20');
INSERT INTO `area_info` VALUES ('2756', '542625', '波密县', '293', '26');
INSERT INTO `area_info` VALUES ('1139', '341822', '广德县', '117', '12');
INSERT INTO `area_info` VALUES ('2342', '511124', '井研县', '250', '23');
INSERT INTO `area_info` VALUES ('1269', '360429', '湖口县', '130', '14');
INSERT INTO `area_info` VALUES ('561', '211321', '朝阳县', '51', '6');
INSERT INTO `area_info` VALUES ('2988', '632221', '门源回族自治县', '320', '29');
INSERT INTO `area_info` VALUES ('2716', '542233', '浪卡子县', '289', '26');
INSERT INTO `area_info` VALUES ('818', '320203', '南长区', '78', '10');
INSERT INTO `area_info` VALUES ('328', '141029', '乡宁县', '25', '4');
INSERT INTO `area_info` VALUES ('2439', '513338', '得荣县', '260', '23');
INSERT INTO `area_info` VALUES ('3180', '140701', '市辖区', '22', '4');
INSERT INTO `area_info` VALUES ('2961', '622923', '永靖县', '316', '28');
INSERT INTO `area_info` VALUES ('3007', '632721', '玉树县', '324', '29');
INSERT INTO `area_info` VALUES ('2209', '469030', '白沙黎族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('2596', '530626', '绥江县', '275', '25');
INSERT INTO `area_info` VALUES ('2108', '450329', '资源县', '223', '20');
INSERT INTO `area_info` VALUES ('2207', '469027', '澄迈县', '237', '21');
INSERT INTO `area_info` VALUES ('2364', '511425', '青神县', '252', '23');
INSERT INTO `area_info` VALUES ('1657', '411527', '淮滨县', '169', '16');
INSERT INTO `area_info` VALUES ('1625', '411302', '宛城区', '167', '16');
INSERT INTO `area_info` VALUES ('3093', '653001', '阿图什市', '339', '31');
INSERT INTO `area_info` VALUES ('3128', '654202', '乌苏市', '343', '31');
INSERT INTO `area_info` VALUES ('109', '130523', '内丘县', '9', '3');
INSERT INTO `area_info` VALUES ('2281', '510304', '大安区', '242', '23');
INSERT INTO `area_info` VALUES ('2942', '621121', '通渭县', '314', '28');
INSERT INTO `area_info` VALUES ('2465', '520122', '息烽县', '262', '24');
INSERT INTO `area_info` VALUES ('2410', '513222', '理　县', '259', '23');
INSERT INTO `area_info` VALUES ('652', '230125', '宾　县', '62', '8');
INSERT INTO `area_info` VALUES ('1795', '430101', '市辖区', '186', '18');
INSERT INTO `area_info` VALUES ('1449', '371122', '莒　县', '148', '15');
INSERT INTO `area_info` VALUES ('400', '150624', '鄂托克旗', '32', '5');
INSERT INTO `area_info` VALUES ('1839', '430522', '新邵县', '190', '18');
INSERT INTO `area_info` VALUES ('2810', '610523', '大荔县', '298', '27');
INSERT INTO `area_info` VALUES ('212', '131123', '武强县', '15', '3');
INSERT INTO `area_info` VALUES ('2228', '500111', '双桥区', '238', '22');
INSERT INTO `area_info` VALUES ('1335', '361101', '市辖区', '137', '14');
INSERT INTO `area_info` VALUES ('1531', '410303', '西工区', '157', '16');
INSERT INTO `area_info` VALUES ('733', '230781', '铁力市', '68', '8');
INSERT INTO `area_info` VALUES ('2528', '522629', '剑河县', '269', '24');
INSERT INTO `area_info` VALUES ('500', '210501', '市辖区', '43', '6');
INSERT INTO `area_info` VALUES ('2425', '513324', '九龙县', '260', '23');
INSERT INTO `area_info` VALUES ('2464', '520121', '开阳县', '262', '24');
INSERT INTO `area_info` VALUES ('1289', '360729', '全南县', '133', '14');
INSERT INTO `area_info` VALUES ('1465', '371329', '临沭县', '150', '15');
INSERT INTO `area_info` VALUES ('1562', '410523', '汤阴县', '159', '16');
INSERT INTO `area_info` VALUES ('1731', '420626', '保康县', '176', '17');
INSERT INTO `area_info` VALUES ('2098', '450305', '七星区', '223', '20');
INSERT INTO `area_info` VALUES ('2139', '450821', '平南县', '228', '20');
INSERT INTO `area_info` VALUES ('2937', '621025', '正宁县', '313', '28');
INSERT INTO `area_info` VALUES ('2084', '450202', '城中区', '222', '20');
INSERT INTO `area_info` VALUES ('485', '210302', '铁东区', '41', '6');
INSERT INTO `area_info` VALUES ('2000', '440923', '电白县', '208', '19');
INSERT INTO `area_info` VALUES ('803', '320102', '玄武区', '77', '10');
INSERT INTO `area_info` VALUES ('3030', '640302', '利通区', '328', '30');
INSERT INTO `area_info` VALUES ('917', '321311', '宿豫区', '89', '10');
INSERT INTO `area_info` VALUES ('2893', '620422', '会宁县', '307', '28');
INSERT INTO `area_info` VALUES ('389', '150521', '科尔沁左翼中旗', '31', '5');
INSERT INTO `area_info` VALUES ('1446', '371102', '东港区', '148', '15');
INSERT INTO `area_info` VALUES ('1760', '421083', '洪湖市', '180', '17');
INSERT INTO `area_info` VALUES ('2203', '469006', '万宁市', '237', '21');
INSERT INTO `area_info` VALUES ('1243', '360111', '青山湖区', '127', '14');
INSERT INTO `area_info` VALUES ('2061', '445222', '揭西县', '219', '19');
INSERT INTO `area_info` VALUES ('2288', '510411', '仁和区', '243', '23');
INSERT INTO `area_info` VALUES ('2104', '450325', '兴安县', '223', '20');
INSERT INTO `area_info` VALUES ('478', '210212', '旅顺口区', '40', '6');
INSERT INTO `area_info` VALUES ('2146', '450924', '兴业县', '229', '20');
INSERT INTO `area_info` VALUES ('889', '320924', '射阳县', '85', '10');
INSERT INTO `area_info` VALUES ('420', '150821', '五原县', '34', '5');
INSERT INTO `area_info` VALUES ('2912', '620723', '临泽县', '310', '28');
INSERT INTO `area_info` VALUES ('1101', '341221', '临泉县', '111', '12');
INSERT INTO `area_info` VALUES ('1430', '370881', '曲阜市', '145', '15');
INSERT INTO `area_info` VALUES ('1140', '341823', '泾　县', '117', '12');
INSERT INTO `area_info` VALUES ('977', '330681', '诸暨市', '95', '11');
INSERT INTO `area_info` VALUES ('422', '150823', '乌拉特前旗', '34', '5');
INSERT INTO `area_info` VALUES ('2936', '621024', '合水县', '313', '28');
INSERT INTO `area_info` VALUES ('995', '330825', '龙游县', '97', '11');
INSERT INTO `area_info` VALUES ('2404', '512001', '市辖区', '258', '23');
INSERT INTO `area_info` VALUES ('2749', '542525', '革吉县', '292', '26');
INSERT INTO `area_info` VALUES ('349', '141182', '汾阳市', '26', '4');
INSERT INTO `area_info` VALUES ('139', '130632', '安新县', '10', '3');
INSERT INTO `area_info` VALUES ('2', '110102', '西城区', '365', '1');
INSERT INTO `area_info` VALUES ('353', '150104', '玉泉区', '27', '5');
INSERT INTO `area_info` VALUES ('2132', '450703', '钦北区', '227', '20');
INSERT INTO `area_info` VALUES ('2564', '530302', '麒麟区', '272', '25');
INSERT INTO `area_info` VALUES ('2338', '511111', '沙湾区', '250', '23');
INSERT INTO `area_info` VALUES ('562', '211322', '建平县', '51', '6');
INSERT INTO `area_info` VALUES ('395', '150581', '霍林郭勒市', '31', '5');
INSERT INTO `area_info` VALUES ('89', '130421', '邯郸县', '8', '3');
INSERT INTO `area_info` VALUES ('1126', '341601', '市辖区', '115', '12');
INSERT INTO `area_info` VALUES ('688', '230401', '市辖区', '65', '8');
INSERT INTO `area_info` VALUES ('1747', '420921', '孝昌县', '179', '17');
INSERT INTO `area_info` VALUES ('3103', '653126', '叶城县', '340', '31');
INSERT INTO `area_info` VALUES ('358', '150124', '清水河县', '27', '5');
INSERT INTO `area_info` VALUES ('3176', '', '嘉模堂区', '376', '34');
INSERT INTO `area_info` VALUES ('2827', '610628', '富　县', '299', '27');
INSERT INTO `area_info` VALUES ('2820', '610621', '延长县', '299', '27');
INSERT INTO `area_info` VALUES ('2120', '450501', '市辖区', '225', '20');
INSERT INTO `area_info` VALUES ('220', '140101', '市辖区', '16', '4');
INSERT INTO `area_info` VALUES ('90', '130423', '临漳县', '8', '3');
INSERT INTO `area_info` VALUES ('1843', '430527', '绥宁县', '190', '18');
INSERT INTO `area_info` VALUES ('2606', '530724', '宁蒗彝族自治县', '276', '25');
INSERT INTO `area_info` VALUES ('418', '150801', '市辖区', '34', '5');
INSERT INTO `area_info` VALUES ('1355', '370124', '平阴县', '138', '15');
INSERT INTO `area_info` VALUES ('959', '330401', '市辖区', '93', '11');
INSERT INTO `area_info` VALUES ('1870', '430821', '慈利县', '193', '18');
INSERT INTO `area_info` VALUES ('1448', '371121', '五莲县', '148', '15');
INSERT INTO `area_info` VALUES ('2973', '623027', '夏河县', '317', '28');
INSERT INTO `area_info` VALUES ('1598', '410902', '华龙区', '163', '16');
INSERT INTO `area_info` VALUES ('2327', '510921', '蓬溪县', '248', '23');
INSERT INTO `area_info` VALUES ('1185', '350502', '鲤城区', '122', '13');
INSERT INTO `area_info` VALUES ('1771', '421181', '麻城市', '181', '17');
INSERT INTO `area_info` VALUES ('2445', '513426', '会东县', '261', '23');
INSERT INTO `area_info` VALUES ('752', '231002', '东安区', '71', '8');
INSERT INTO `area_info` VALUES ('1418', '370785', '高密市', '144', '15');
INSERT INTO `area_info` VALUES ('416', '150784', '额尔古纳市', '33', '5');
INSERT INTO `area_info` VALUES ('1045', '340323', '固镇县', '103', '12');
INSERT INTO `area_info` VALUES ('782', '232723', '漠河县', '74', '8');
INSERT INTO `area_info` VALUES ('2974', '630101', '市辖区', '318', '29');
INSERT INTO `area_info` VALUES ('2648', '532531', '绿春县', '280', '25');
INSERT INTO `area_info` VALUES ('3174', '', '风顺堂区', '375', '34');
INSERT INTO `area_info` VALUES ('274', '140622', '应　县', '21', '4');
INSERT INTO `area_info` VALUES ('2284', '510322', '富顺县', '242', '23');
INSERT INTO `area_info` VALUES ('410', '150725', '陈巴尔虎旗', '33', '5');
INSERT INTO `area_info` VALUES ('1405', '370686', '栖霞市', '143', '15');
INSERT INTO `area_info` VALUES ('999', '330903', '普陀区', '98', '11');
INSERT INTO `area_info` VALUES ('1409', '370703', '寒亭区', '144', '15');
INSERT INTO `area_info` VALUES ('1960', '440308', '盐田区', '202', '19');
INSERT INTO `area_info` VALUES ('1097', '341201', '市辖区', '111', '12');
INSERT INTO `area_info` VALUES ('1502', '371726', '鄄城县', '154', '15');
INSERT INTO `area_info` VALUES ('1429', '370832', '梁山县', '145', '15');
INSERT INTO `area_info` VALUES ('2956', '621227', '徽　县', '315', '28');
INSERT INTO `area_info` VALUES ('1594', '410881', '济源市', '162', '16');
INSERT INTO `area_info` VALUES ('1417', '370784', '安丘市', '144', '15');
INSERT INTO `area_info` VALUES ('3088', '652925', '新和县', '338', '31');
INSERT INTO `area_info` VALUES ('1167', '350303', '涵江区', '120', '13');
INSERT INTO `area_info` VALUES ('386', '150430', '敖汉旗', '30', '5');
INSERT INTO `area_info` VALUES ('2334', '511025', '资中县', '249', '23');
INSERT INTO `area_info` VALUES ('943', '330226', '宁海县', '91', '11');
INSERT INTO `area_info` VALUES ('1555', '410482', '汝州市', '158', '16');
INSERT INTO `area_info` VALUES ('1040', '340303', '蚌山区', '103', '12');
INSERT INTO `area_info` VALUES ('1875', '430921', '南　县', '194', '18');
INSERT INTO `area_info` VALUES ('2324', '510901', '市辖区', '248', '23');
INSERT INTO `area_info` VALUES ('945', '330282', '慈溪市', '91', '11');
INSERT INTO `area_info` VALUES ('577', '220105', '二道区', '53', '7');
INSERT INTO `area_info` VALUES ('1186', '350503', '丰泽区', '122', '13');
INSERT INTO `area_info` VALUES ('1081', '341002', '屯溪区', '109', '12');
INSERT INTO `area_info` VALUES ('2128', '450621', '上思县', '226', '20');
INSERT INTO `area_info` VALUES ('1695', '420201', '市辖区', '173', '17');
INSERT INTO `area_info` VALUES ('2411', '513223', '茂　县', '259', '23');
INSERT INTO `area_info` VALUES ('2661', '532901', '大理市', '283', '25');
INSERT INTO `area_info` VALUES ('1067', '340721', '铜陵县', '107', '12');
INSERT INTO `area_info` VALUES ('1162', '350211', '集美区', '119', '13');
INSERT INTO `area_info` VALUES ('2933', '621021', '庆城县', '313', '28');
INSERT INTO `area_info` VALUES ('2160', '451031', '隆林各族自治县', '230', '20');
INSERT INTO `area_info` VALUES ('2996', '632521', '共和县', '322', '29');
INSERT INTO `area_info` VALUES ('2193', '460101', '市辖区', '235', '21');
INSERT INTO `area_info` VALUES ('211', '131122', '武邑县', '15', '3');
INSERT INTO `area_info` VALUES ('2759', '610101', '市辖区', '294', '27');
INSERT INTO `area_info` VALUES ('859', '320611', '港闸区', '82', '10');
INSERT INTO `area_info` VALUES ('2513', '522422', '大方县', '268', '24');
INSERT INTO `area_info` VALUES ('1706', '420322', '郧西县', '174', '17');
INSERT INTO `area_info` VALUES ('1467', '371402', '德城区', '151', '15');
INSERT INTO `area_info` VALUES ('2049', '441825', '连山壮族瑶族自治县', '215', '19');
INSERT INTO `area_info` VALUES ('2636', '532331', '禄丰县', '279', '25');
INSERT INTO `area_info` VALUES ('1443', '371082', '荣成市', '147', '15');
INSERT INTO `area_info` VALUES ('2910', '620721', '肃南裕固族自治县', '310', '28');
INSERT INTO `area_info` VALUES ('2248', '500237', '巫山县', '239', '22');
INSERT INTO `area_info` VALUES ('1615', '411121', '舞阳县', '165', '16');
INSERT INTO `area_info` VALUES ('1296', '360781', '瑞金市', '133', '14');
INSERT INTO `area_info` VALUES ('2156', '451027', '凌云县', '230', '20');
INSERT INTO `area_info` VALUES ('2784', '610324', '扶风县', '296', '27');
INSERT INTO `area_info` VALUES ('1930', '440101', '市辖区', '200', '19');
INSERT INTO `area_info` VALUES ('3182', '442001', '市辖区', '217', '19');
INSERT INTO `area_info` VALUES ('1169', '350305', '秀屿区', '120', '13');
INSERT INTO `area_info` VALUES ('486', '210303', '铁西区', '41', '6');
INSERT INTO `area_info` VALUES ('2966', '623001', '合作市', '317', '28');
INSERT INTO `area_info` VALUES ('1473', '371426', '平原县', '151', '15');
INSERT INTO `area_info` VALUES ('315', '140931', '保德县', '24', '4');
INSERT INTO `area_info` VALUES ('483', '210283', '庄河市', '40', '6');
INSERT INTO `area_info` VALUES ('2855', '610830', '清涧县', '301', '27');
INSERT INTO `area_info` VALUES ('1247', '360124', '进贤县', '127', '14');
INSERT INTO `area_info` VALUES ('1511', '410108', '邙山区', '155', '16');
INSERT INTO `area_info` VALUES ('2214', '469036', '琼中黎族苗族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('1919', '431322', '新化县', '198', '18');
INSERT INTO `area_info` VALUES ('1458', '371322', '郯城县', '150', '15');
INSERT INTO `area_info` VALUES ('337', '141102', '离石区', '26', '4');
INSERT INTO `area_info` VALUES ('1898', '431125', '江永县', '196', '18');
INSERT INTO `area_info` VALUES ('2611', '530823', '景东彝族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('870', '320721', '赣榆县', '83', '10');
INSERT INTO `area_info` VALUES ('426', '150901', '市辖区', '35', '5');
INSERT INTO `area_info` VALUES ('1095', '341181', '天长市', '110', '12');
INSERT INTO `area_info` VALUES ('1626', '411303', '卧龙区', '167', '16');
INSERT INTO `area_info` VALUES ('1060', '340603', '相山区', '106', '12');
INSERT INTO `area_info` VALUES ('710', '230605', '红岗区', '67', '8');
INSERT INTO `area_info` VALUES ('45', '130123', '正定县', '5', '3');
INSERT INTO `area_info` VALUES ('2852', '610827', '米脂县', '301', '27');
INSERT INTO `area_info` VALUES ('1642', '411422', '睢　县', '168', '16');
INSERT INTO `area_info` VALUES ('1337', '361121', '上饶县', '137', '14');
INSERT INTO `area_info` VALUES ('3025', '640201', '市辖区', '327', '30');
INSERT INTO `area_info` VALUES ('1838', '430521', '邵东县', '190', '18');
INSERT INTO `area_info` VALUES ('83', '130324', '卢龙县', '7', '3');
INSERT INTO `area_info` VALUES ('1558', '410503', '北关区', '159', '16');
INSERT INTO `area_info` VALUES ('1861', '430722', '汉寿县', '192', '18');
INSERT INTO `area_info` VALUES ('1034', '340207', '鸠江区', '102', '12');
INSERT INTO `area_info` VALUES ('2723', '542327', '昂仁县', '290', '26');
INSERT INTO `area_info` VALUES ('1130', '341623', '利辛县', '115', '12');
INSERT INTO `area_info` VALUES ('2301', '510623', '中江县', '245', '23');
INSERT INTO `area_info` VALUES ('2929', '620981', '玉门市', '312', '28');
INSERT INTO `area_info` VALUES ('2677', '533124', '陇川县', '284', '25');
INSERT INTO `area_info` VALUES ('610', '220523', '辉南县', '57', '7');
INSERT INTO `area_info` VALUES ('162', '130728', '怀安县', '11', '3');
INSERT INTO `area_info` VALUES ('2042', '441721', '阳西县', '214', '19');
INSERT INTO `area_info` VALUES ('295', '140825', '新绛县', '23', '4');
INSERT INTO `area_info` VALUES ('1349', '370102', '历下区', '138', '15');
INSERT INTO `area_info` VALUES ('166', '130732', '赤城县', '11', '3');
INSERT INTO `area_info` VALUES ('388', '150502', '科尔沁区', '31', '5');
INSERT INTO `area_info` VALUES ('308', '140924', '繁峙县', '24', '4');
INSERT INTO `area_info` VALUES ('627', '220801', '市辖区', '60', '7');
INSERT INTO `area_info` VALUES ('1532', '410304', '廛河回族区', '157', '16');
INSERT INTO `area_info` VALUES ('1617', '411201', '市辖区', '166', '16');
INSERT INTO `area_info` VALUES ('2110', '450331', '荔蒲县', '223', '20');
INSERT INTO `area_info` VALUES ('2317', '510802', '市中区', '247', '23');
INSERT INTO `area_info` VALUES ('2796', '610423', '泾阳县', '297', '27');
INSERT INTO `area_info` VALUES ('1749', '420923', '云梦县', '179', '17');
INSERT INTO `area_info` VALUES ('2755', '542624', '墨脱县', '293', '26');
INSERT INTO `area_info` VALUES ('530', '210902', '海州区', '47', '6');
INSERT INTO `area_info` VALUES ('846', '320502', '沧浪区', '81', '10');
INSERT INTO `area_info` VALUES ('1131', '341701', '市辖区', '116', '12');
INSERT INTO `area_info` VALUES ('989', '330784', '永康市', '96', '11');
INSERT INTO `area_info` VALUES ('227', '140121', '清徐县', '16', '4');
INSERT INTO `area_info` VALUES ('3192', '330922', '市辖区', '386', '17');
INSERT INTO `area_info` VALUES ('992', '330803', '衢江区', '97', '11');
INSERT INTO `area_info` VALUES ('2931', '621001', '市辖区', '313', '28');
INSERT INTO `area_info` VALUES ('1275', '360601', '市辖区', '132', '14');
INSERT INTO `area_info` VALUES ('2473', '520302', '红花岗区', '264', '24');
INSERT INTO `area_info` VALUES ('342', '141125', '柳林县', '26', '4');
INSERT INTO `area_info` VALUES ('2520', '522601', '凯里市', '269', '24');
INSERT INTO `area_info` VALUES ('2360', '511421', '仁寿县', '252', '23');
INSERT INTO `area_info` VALUES ('911', '321281', '兴化市', '88', '10');
INSERT INTO `area_info` VALUES ('1885', '431024', '嘉禾县', '195', '18');
INSERT INTO `area_info` VALUES ('866', '320701', '市辖区', '83', '10');
INSERT INTO `area_info` VALUES ('1396', '370611', '福山区', '143', '15');
INSERT INTO `area_info` VALUES ('2119', '450481', '岑溪市', '224', '20');
INSERT INTO `area_info` VALUES ('1797', '430103', '天心区', '186', '18');
INSERT INTO `area_info` VALUES ('2480', '520326', '务川仡佬族苗族自治县', '264', '24');
INSERT INTO `area_info` VALUES ('2352', '511321', '南部县', '251', '23');
INSERT INTO `area_info` VALUES ('228', '140122', '阳曲县', '16', '4');
INSERT INTO `area_info` VALUES ('453', '152529', '正镶白旗', '37', '5');
INSERT INTO `area_info` VALUES ('1696', '420202', '黄石港区', '173', '17');
INSERT INTO `area_info` VALUES ('1521', '410204', '鼓楼区', '156', '16');
INSERT INTO `area_info` VALUES ('570', '211421', '绥中县', '52', '6');
INSERT INTO `area_info` VALUES ('641', '230101', '市辖区', '62', '8');
INSERT INTO `area_info` VALUES ('694', '230407', '兴山区', '65', '8');
INSERT INTO `area_info` VALUES ('2888', '620321', '永昌县', '306', '28');
INSERT INTO `area_info` VALUES ('586', '220203', '龙潭区', '54', '7');
INSERT INTO `area_info` VALUES ('863', '320682', '如皋市', '82', '10');
INSERT INTO `area_info` VALUES ('1583', '410781', '卫辉市', '161', '16');
INSERT INTO `area_info` VALUES ('1998', '440902', '茂南区', '208', '19');
INSERT INTO `area_info` VALUES ('2164', '451122', '钟山县', '231', '20');
INSERT INTO `area_info` VALUES ('2379', '511622', '武胜县', '254', '23');
INSERT INTO `area_info` VALUES ('2702', '542129', '芒康县', '288', '26');
INSERT INTO `area_info` VALUES ('1650', '411503', '平桥区', '169', '16');
INSERT INTO `area_info` VALUES ('579', '220112', '双阳区', '53', '7');
INSERT INTO `area_info` VALUES ('46', '130124', '栾城县', '5', '3');
INSERT INTO `area_info` VALUES ('2590', '530602', '昭阳区', '275', '25');
INSERT INTO `area_info` VALUES ('607', '220502', '东昌区', '57', '7');
INSERT INTO `area_info` VALUES ('2491', '520423', '镇宁布依族苗族自治县', '265', '24');
INSERT INTO `area_info` VALUES ('2062', '445224', '惠来县', '219', '19');
INSERT INTO `area_info` VALUES ('2623', '530924', '镇康县', '278', '25');
INSERT INTO `area_info` VALUES ('2015', '441303', '惠阳区', '210', '19');
INSERT INTO `area_info` VALUES ('1542', '410328', '洛宁县', '157', '16');
INSERT INTO `area_info` VALUES ('225', '140109', '万柏林区', '16', '4');
INSERT INTO `area_info` VALUES ('178', '130827', '宽城满族自治县', '12', '3');
INSERT INTO `area_info` VALUES ('1062', '340621', '濉溪县', '106', '12');
INSERT INTO `area_info` VALUES ('2131', '450702', '钦南区', '227', '20');
INSERT INTO `area_info` VALUES ('2920', '620824', '华亭县', '311', '28');
INSERT INTO `area_info` VALUES ('1827', '430421', '衡阳县', '189', '18');
INSERT INTO `area_info` VALUES ('2212', '469034', '陵水黎族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('1559', '410505', '殷都区', '159', '16');
INSERT INTO `area_info` VALUES ('2831', '610632', '黄陵县', '299', '27');
INSERT INTO `area_info` VALUES ('2173', '451226', '环江毛南族自治县', '232', '20');
INSERT INTO `area_info` VALUES ('878', '320811', '清浦区', '84', '10');
INSERT INTO `area_info` VALUES ('1360', '370202', '市南区', '139', '15');
INSERT INTO `area_info` VALUES ('3100', '653123', '英吉沙县', '340', '31');
INSERT INTO `area_info` VALUES ('3118', '654003', '奎屯市', '342', '31');
INSERT INTO `area_info` VALUES ('2461', '520112', '乌当区', '262', '24');
INSERT INTO `area_info` VALUES ('2865', '610927', '镇坪县', '302', '27');
INSERT INTO `area_info` VALUES ('331', '141032', '永和县', '25', '4');
INSERT INTO `area_info` VALUES ('2618', '530901', '市辖区', '278', '25');
INSERT INTO `area_info` VALUES ('1693', '420116', '黄陂区', '172', '17');
INSERT INTO `area_info` VALUES ('238', '140223', '广灵县', '17', '4');
INSERT INTO `area_info` VALUES ('3108', '653131', '塔什库尔干塔吉克自治县', '340', '31');
INSERT INTO `area_info` VALUES ('727', '230712', '汤旺河区', '68', '8');
INSERT INTO `area_info` VALUES ('2277', '510184', '崇州市', '241', '23');
INSERT INTO `area_info` VALUES ('936', '330203', '海曙区', '91', '11');
INSERT INTO `area_info` VALUES ('663', '230203', '建华区', '63', '8');
INSERT INTO `area_info` VALUES ('526', '210811', '老边区', '46', '6');
INSERT INTO `area_info` VALUES ('2234', '500223', '潼南县', '239', '22');
INSERT INTO `area_info` VALUES ('1737', '420703', '华容区', '177', '17');
INSERT INTO `area_info` VALUES ('1387', '370481', '滕州市', '141', '15');
INSERT INTO `area_info` VALUES ('558', '211301', '市辖区', '51', '6');
INSERT INTO `area_info` VALUES ('103', '130481', '武安市', '8', '3');
INSERT INTO `area_info` VALUES ('2403', '511923', '平昌县', '257', '23');
INSERT INTO `area_info` VALUES ('1056', '340504', '雨山区', '105', '12');
INSERT INTO `area_info` VALUES ('2386', '511723', '开江县', '255', '23');
INSERT INTO `area_info` VALUES ('1775', '421221', '嘉鱼县', '182', '17');
INSERT INTO `area_info` VALUES ('2166', '451201', '市辖区', '232', '20');
INSERT INTO `area_info` VALUES ('2337', '511102', '市中区', '250', '23');
INSERT INTO `area_info` VALUES ('1661', '411621', '扶沟县', '170', '16');
INSERT INTO `area_info` VALUES ('736', '230803', '向阳区', '69', '8');
INSERT INTO `area_info` VALUES ('2428', '513327', '炉霍县', '260', '23');
INSERT INTO `area_info` VALUES ('159', '130725', '尚义县', '11', '3');
INSERT INTO `area_info` VALUES ('2422', '513321', '康定县', '260', '23');
INSERT INTO `area_info` VALUES ('2309', '510722', '三台县', '246', '23');
INSERT INTO `area_info` VALUES ('2058', '445201', '市辖区', '219', '19');
INSERT INTO `area_info` VALUES ('73', '130230', '唐海县', '6', '3');
INSERT INTO `area_info` VALUES ('2667', '532927', '巍山彝族回族自治县', '283', '25');
INSERT INTO `area_info` VALUES ('1640', '411403', '睢阳区', '168', '16');
INSERT INTO `area_info` VALUES ('3071', '652328', '木垒哈萨克自治县', '335', '31');
INSERT INTO `area_info` VALUES ('2486', '520382', '仁怀市', '264', '24');
INSERT INTO `area_info` VALUES ('707', '230602', '萨尔图区', '67', '8');
INSERT INTO `area_info` VALUES ('2471', '520222', '盘　县', '263', '24');
INSERT INTO `area_info` VALUES ('442', '152223', '扎赉特旗', '36', '5');
INSERT INTO `area_info` VALUES ('764', '231121', '嫩江县', '72', '8');
INSERT INTO `area_info` VALUES ('951', '330322', '洞头县', '92', '11');
INSERT INTO `area_info` VALUES ('1114', '341421', '庐江县', '113', '12');
INSERT INTO `area_info` VALUES ('2141', '450901', '市辖区', '229', '20');
INSERT INTO `area_info` VALUES ('86', '130403', '丛台区', '8', '3');
INSERT INTO `area_info` VALUES ('2972', '623026', '碌曲县', '317', '28');
INSERT INTO `area_info` VALUES ('2957', '621228', '两当县', '315', '28');
INSERT INTO `area_info` VALUES ('284', '140726', '太谷县', '368', '4');
INSERT INTO `area_info` VALUES ('1877', '430923', '安化县', '194', '18');
INSERT INTO `area_info` VALUES ('1251', '360222', '浮梁县', '128', '14');
INSERT INTO `area_info` VALUES ('1138', '341821', '郎溪县', '117', '12');
INSERT INTO `area_info` VALUES ('2190', '451424', '大新县', '234', '20');
INSERT INTO `area_info` VALUES ('3065', '652302', '阜康市', '335', '31');
INSERT INTO `area_info` VALUES ('2065', '445302', '云城区', '220', '19');
INSERT INTO `area_info` VALUES ('1772', '421182', '武穴市', '181', '17');
INSERT INTO `area_info` VALUES ('2680', '533324', '贡山独龙族怒族自治县', '285', '25');
INSERT INTO `area_info` VALUES ('1015', '331122', '缙云县', '100', '11');
INSERT INTO `area_info` VALUES ('765', '231123', '逊克县', '72', '8');
INSERT INTO `area_info` VALUES ('2967', '623021', '临潭县', '317', '28');
INSERT INTO `area_info` VALUES ('1231', '350922', '古田县', '126', '13');
INSERT INTO `area_info` VALUES ('1921', '431382', '涟源市', '198', '18');
INSERT INTO `area_info` VALUES ('2289', '510421', '米易县', '243', '23');
INSERT INTO `area_info` VALUES ('3020', '640105', '西夏区', '326', '30');
INSERT INTO `area_info` VALUES ('1679', '411728', '遂平县', '171', '16');
INSERT INTO `area_info` VALUES ('3011', '632725', '囊谦县', '324', '29');
INSERT INTO `area_info` VALUES ('1125', '341525', '霍山县', '114', '12');
INSERT INTO `area_info` VALUES ('1868', '430802', '永定区', '193', '18');
INSERT INTO `area_info` VALUES ('674', '230229', '克山县', '63', '8');
INSERT INTO `area_info` VALUES ('2367', '511521', '宜宾县', '253', '23');
INSERT INTO `area_info` VALUES ('1428', '370831', '泗水县', '145', '15');
INSERT INTO `area_info` VALUES ('451', '152527', '太仆寺旗', '37', '5');
INSERT INTO `area_info` VALUES ('3081', '652827', '和静县', '337', '31');
INSERT INTO `area_info` VALUES ('2215', '469037', '西沙群岛', '237', '21');
INSERT INTO `area_info` VALUES ('1635', '411329', '新野县', '167', '16');
INSERT INTO `area_info` VALUES ('1792', '429005', '潜江市', '185', '17');
INSERT INTO `area_info` VALUES ('2921', '620825', '庄浪县', '311', '28');
INSERT INTO `area_info` VALUES ('1999', '440903', '茂港区', '208', '19');
INSERT INTO `area_info` VALUES ('2825', '610626', '吴旗县', '299', '27');
INSERT INTO `area_info` VALUES ('1093', '341125', '定远县', '110', '12');
INSERT INTO `area_info` VALUES ('1239', '360102', '东湖区', '127', '14');
INSERT INTO `area_info` VALUES ('397', '150621', '达拉特旗', '32', '5');
INSERT INTO `area_info` VALUES ('830', '320311', '泉山区', '79', '10');
INSERT INTO `area_info` VALUES ('1205', '350627', '南靖县', '123', '13');
INSERT INTO `area_info` VALUES ('2778', '610301', '市辖区', '296', '27');
INSERT INTO `area_info` VALUES ('1643', '411423', '宁陵县', '168', '16');
INSERT INTO `area_info` VALUES ('2328', '510922', '射洪县', '248', '23');
INSERT INTO `area_info` VALUES ('2170', '451223', '凤山县', '232', '20');
INSERT INTO `area_info` VALUES ('1032', '340203', '马塘区', '102', '12');
INSERT INTO `area_info` VALUES ('2797', '610424', '乾　县', '297', '27');
INSERT INTO `area_info` VALUES ('2114', '450404', '蝶山区', '224', '20');
INSERT INTO `area_info` VALUES ('1035', '340221', '芜湖县', '102', '12');
INSERT INTO `area_info` VALUES ('717', '230702', '伊春区', '68', '8');
INSERT INTO `area_info` VALUES ('149', '130684', '高碑店市', '10', '3');
INSERT INTO `area_info` VALUES ('967', '330501', '市辖区', '94', '11');
INSERT INTO `area_info` VALUES ('1800', '430111', '雨花区', '186', '18');
INSERT INTO `area_info` VALUES ('976', '330624', '新昌县', '95', '11');
INSERT INTO `area_info` VALUES ('1819', '430381', '湘乡市', '188', '18');
INSERT INTO `area_info` VALUES ('949', '330303', '龙湾区', '92', '11');
INSERT INTO `area_info` VALUES ('3033', '640381', '青铜峡市', '328', '30');
INSERT INTO `area_info` VALUES ('3058', '652101', '吐鲁番市', '333', '31');
INSERT INTO `area_info` VALUES ('2106', '450327', '灌阳县', '223', '20');
INSERT INTO `area_info` VALUES ('263', '140501', '市辖区', '20', '4');
INSERT INTO `area_info` VALUES ('3178', '', '圣方济各堂区', '376', '34');
INSERT INTO `area_info` VALUES ('3168', '', '观塘区', '373', '33');
INSERT INTO `area_info` VALUES ('545', '211101', '市辖区', '49', '6');
INSERT INTO `area_info` VALUES ('1925', '433124', '花垣县', '199', '18');
INSERT INTO `area_info` VALUES ('2769', '610122', '蓝田县', '294', '27');
INSERT INTO `area_info` VALUES ('316', '140932', '偏关县', '24', '4');
INSERT INTO `area_info` VALUES ('1587', '410803', '中站区', '162', '16');
INSERT INTO `area_info` VALUES ('133', '130626', '定兴县', '10', '3');
INSERT INTO `area_info` VALUES ('2244', '500233', '忠　县', '239', '22');
INSERT INTO `area_info` VALUES ('2074', '450107', '西乡塘区', '221', '20');
INSERT INTO `area_info` VALUES ('2953', '621224', '康　县', '315', '28');
INSERT INTO `area_info` VALUES ('147', '130682', '定州市', '10', '3');
INSERT INTO `area_info` VALUES ('1090', '341103', '南谯区', '110', '12');
INSERT INTO `area_info` VALUES ('2853', '610828', '佳　县', '301', '27');
INSERT INTO `area_info` VALUES ('640', '222426', '安图县', '61', '7');
INSERT INTO `area_info` VALUES ('1722', '420581', '宜都市', '175', '17');
INSERT INTO `area_info` VALUES ('1488', '371602', '滨城区', '153', '15');
INSERT INTO `area_info` VALUES ('2941', '621102', '安定区', '314', '28');
INSERT INTO `area_info` VALUES ('3024', '640181', '灵武市', '326', '30');
INSERT INTO `area_info` VALUES ('1250', '360203', '珠山区', '128', '14');
INSERT INTO `area_info` VALUES ('1341', '361125', '横峰县', '137', '14');
INSERT INTO `area_info` VALUES ('840', '320405', '戚墅堰区', '80', '10');
INSERT INTO `area_info` VALUES ('2142', '450902', '玉州区', '229', '20');
INSERT INTO `area_info` VALUES ('957', '330381', '瑞安市', '92', '11');
INSERT INTO `area_info` VALUES ('2574', '530402', '红塔区', '273', '25');
INSERT INTO `area_info` VALUES ('2355', '511324', '仪陇县', '251', '23');
INSERT INTO `area_info` VALUES ('1931', '440102', '东山区', '200', '19');
INSERT INTO `area_info` VALUES ('1765', '421122', '红安县', '181', '17');
INSERT INTO `area_info` VALUES ('1969', '440513', '潮阳区', '204', '19');
INSERT INTO `area_info` VALUES ('2757', '542626', '察隅县', '293', '26');
INSERT INTO `area_info` VALUES ('3074', '652723', '温泉县', '336', '31');
INSERT INTO `area_info` VALUES ('508', '210602', '元宝区', '44', '6');
INSERT INTO `area_info` VALUES ('2901', '620524', '武山县', '308', '28');
INSERT INTO `area_info` VALUES ('2793', '610403', '杨凌区', '297', '27');
INSERT INTO `area_info` VALUES ('2604', '530722', '永胜县', '276', '25');
INSERT INTO `area_info` VALUES ('198', '131002', '安次区', '14', '3');
INSERT INTO `area_info` VALUES ('645', '230106', '香坊区', '62', '8');
INSERT INTO `area_info` VALUES ('612', '220581', '梅河口市', '57', '7');
INSERT INTO `area_info` VALUES ('2143', '450921', '容　县', '229', '20');
INSERT INTO `area_info` VALUES ('74', '130281', '遵化市', '6', '3');
INSERT INTO `area_info` VALUES ('605', '220422', '东辽县', '56', '7');
INSERT INTO `area_info` VALUES ('1927', '433126', '古丈县', '199', '18');
INSERT INTO `area_info` VALUES ('1846', '430581', '武冈市', '190', '18');
INSERT INTO `area_info` VALUES ('1985', '440784', '鹤山市', '206', '19');
INSERT INTO `area_info` VALUES ('354', '150105', '赛罕区', '27', '5');
INSERT INTO `area_info` VALUES ('3028', '640221', '平罗县', '327', '30');
INSERT INTO `area_info` VALUES ('1413', '370725', '昌乐县', '144', '15');
INSERT INTO `area_info` VALUES ('1866', '430781', '津市市', '192', '18');
INSERT INTO `area_info` VALUES ('2616', '530828', '澜沧拉祜族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('2656', '532627', '广南县', '281', '25');
INSERT INTO `area_info` VALUES ('2695', '542122', '江达县', '288', '26');
INSERT INTO `area_info` VALUES ('2523', '522624', '三穗县', '269', '24');
INSERT INTO `area_info` VALUES ('1940', '440114', '花都区', '200', '19');
INSERT INTO `area_info` VALUES ('2522', '522623', '施秉县', '269', '24');
INSERT INTO `area_info` VALUES ('1001', '330922', '嵊泗县', '98', '11');
INSERT INTO `area_info` VALUES ('3101', '653124', '泽普县', '340', '31');
INSERT INTO `area_info` VALUES ('2310', '510723', '盐亭县', '246', '23');
INSERT INTO `area_info` VALUES ('2286', '510402', '东　区', '243', '23');
INSERT INTO `area_info` VALUES ('491', '210381', '海城市', '41', '6');
INSERT INTO `area_info` VALUES ('1581', '410727', '封丘县', '161', '16');
INSERT INTO `area_info` VALUES ('2660', '532823', '勐腊县', '282', '25');
INSERT INTO `area_info` VALUES ('2006', '441203', '鼎湖区', '209', '19');
INSERT INTO `area_info` VALUES ('343', '141126', '石楼县', '26', '4');
INSERT INTO `area_info` VALUES ('682', '230305', '梨树区', '64', '8');
INSERT INTO `area_info` VALUES ('761', '231085', '穆棱市', '71', '8');
INSERT INTO `area_info` VALUES ('2622', '530923', '永德县', '278', '25');
INSERT INTO `area_info` VALUES ('9', '110109', '门头沟区', '365', '1');
INSERT INTO `area_info` VALUES ('1900', '431127', '蓝山县', '196', '18');
INSERT INTO `area_info` VALUES ('1882', '431021', '桂阳县', '195', '18');
INSERT INTO `area_info` VALUES ('772', '231222', '兰西县', '73', '8');
INSERT INTO `area_info` VALUES ('493', '210402', '新抚区', '42', '6');
INSERT INTO `area_info` VALUES ('569', '211404', '南票区', '52', '6');
INSERT INTO `area_info` VALUES ('55', '130133', '赵　县', '5', '3');
INSERT INTO `area_info` VALUES ('294', '140824', '稷山县', '23', '4');
INSERT INTO `area_info` VALUES ('982', '330703', '金东区', '96', '11');
INSERT INTO `area_info` VALUES ('2886', '620301', '市辖区', '306', '28');
INSERT INTO `area_info` VALUES ('1245', '360122', '新建县', '127', '14');
INSERT INTO `area_info` VALUES ('6', '110106', '丰台区', '365', '1');
INSERT INTO `area_info` VALUES ('762', '231101', '市辖区', '72', '8');
INSERT INTO `area_info` VALUES ('2809', '610522', '潼关县', '298', '27');
INSERT INTO `area_info` VALUES ('1132', '341702', '贵池区', '116', '12');
INSERT INTO `area_info` VALUES ('952', '330324', '永嘉县', '92', '11');
INSERT INTO `area_info` VALUES ('2631', '532325', '姚安县', '279', '25');
INSERT INTO `area_info` VALUES ('2764', '610112', '未央区', '294', '27');
INSERT INTO `area_info` VALUES ('1783', '422801', '恩施市', '184', '17');
INSERT INTO `area_info` VALUES ('2346', '511133', '马边彝族自治县', '250', '23');
INSERT INTO `area_info` VALUES ('1668', '411628', '鹿邑县', '170', '16');
INSERT INTO `area_info` VALUES ('1757', '421023', '监利县', '180', '17');
INSERT INTO `area_info` VALUES ('2696', '542123', '贡觉县', '288', '26');
INSERT INTO `area_info` VALUES ('1009', '331024', '仙居县', '99', '11');
INSERT INTO `area_info` VALUES ('3191', '330922', '市辖区', '385', '17');
INSERT INTO `area_info` VALUES ('998', '330902', '定海区', '98', '11');
INSERT INTO `area_info` VALUES ('230', '140181', '古交市', '16', '4');
INSERT INTO `area_info` VALUES ('1229', '350902', '蕉城区', '126', '13');
INSERT INTO `area_info` VALUES ('2990', '632223', '海晏县', '320', '29');
INSERT INTO `area_info` VALUES ('576', '220104', '朝阳区', '53', '7');
INSERT INTO `area_info` VALUES ('2595', '530625', '永善县', '275', '25');
INSERT INTO `area_info` VALUES ('247', '140321', '平定县', '18', '4');
INSERT INTO `area_info` VALUES ('2798', '610425', '礼泉县', '297', '27');
INSERT INTO `area_info` VALUES ('1817', '430304', '岳塘区', '188', '18');
INSERT INTO `area_info` VALUES ('378', '150421', '阿鲁科尔沁旗', '30', '5');
INSERT INTO `area_info` VALUES ('300', '140830', '芮城县', '23', '4');
INSERT INTO `area_info` VALUES ('3107', '653130', '巴楚县', '340', '31');
INSERT INTO `area_info` VALUES ('2382', '511701', '市辖区', '255', '23');
INSERT INTO `area_info` VALUES ('1068', '340801', '市辖区', '108', '12');
INSERT INTO `area_info` VALUES ('1454', '371302', '兰山区', '150', '15');
INSERT INTO `area_info` VALUES ('2444', '513425', '会理县', '261', '23');
INSERT INTO `area_info` VALUES ('3106', '653129', '伽师县', '340', '31');
INSERT INTO `area_info` VALUES ('1338', '361122', '广丰县', '137', '14');
INSERT INTO `area_info` VALUES ('2907', '620623', '天祝藏族自治县', '309', '28');
INSERT INTO `area_info` VALUES ('849', '320505', '虎丘区', '81', '10');
INSERT INTO `area_info` VALUES ('2869', '611002', '商州区', '303', '27');
INSERT INTO `area_info` VALUES ('84', '130401', '市辖区', '8', '3');
INSERT INTO `area_info` VALUES ('367', '150221', '土默特右旗', '28', '5');
INSERT INTO `area_info` VALUES ('1527', '410224', '开封县', '156', '16');
INSERT INTO `area_info` VALUES ('2274', '510181', '都江堰市', '241', '23');
INSERT INTO `area_info` VALUES ('2018', '441324', '龙门县', '210', '19');
INSERT INTO `area_info` VALUES ('723', '230708', '美溪区', '68', '8');
INSERT INTO `area_info` VALUES ('240', '140225', '浑源县', '17', '4');
INSERT INTO `area_info` VALUES ('2607', '530801', '市辖区', '277', '25');
INSERT INTO `area_info` VALUES ('2176', '451229', '大化瑶族自治县', '232', '20');
INSERT INTO `area_info` VALUES ('205', '131028', '大厂回族自治县', '14', '3');
INSERT INTO `area_info` VALUES ('2456', '513437', '雷波县', '261', '23');
INSERT INTO `area_info` VALUES ('365', '150206', '白云矿区', '28', '5');
INSERT INTO `area_info` VALUES ('95', '130428', '肥乡县', '8', '3');
INSERT INTO `area_info` VALUES ('2748', '542524', '日土县', '292', '26');
INSERT INTO `area_info` VALUES ('2489', '520421', '平坝县', '265', '24');
INSERT INTO `area_info` VALUES ('2111', '450332', '恭城瑶族自治县', '223', '20');
INSERT INTO `area_info` VALUES ('3094', '653022', '阿克陶县', '339', '31');
INSERT INTO `area_info` VALUES ('2709', '542225', '琼结县', '289', '26');
INSERT INTO `area_info` VALUES ('306', '140922', '五台县', '24', '4');
INSERT INTO `area_info` VALUES ('966', '330483', '桐乡市', '93', '11');
INSERT INTO `area_info` VALUES ('845', '320501', '市辖区', '81', '10');
INSERT INTO `area_info` VALUES ('2887', '620302', '金川区', '306', '28');
INSERT INTO `area_info` VALUES ('900', '321088', '江都市', '86', '10');
INSERT INTO `area_info` VALUES ('267', '140524', '陵川县', '20', '4');
INSERT INTO `area_info` VALUES ('1508', '410104', '管城回族区', '155', '16');
INSERT INTO `area_info` VALUES ('249', '140401', '市辖区', '19', '4');
INSERT INTO `area_info` VALUES ('2381', '511681', '华莹市', '254', '23');
INSERT INTO `area_info` VALUES ('1444', '371083', '乳山市', '147', '15');
INSERT INTO `area_info` VALUES ('1014', '331121', '青田县', '100', '11');
INSERT INTO `area_info` VALUES ('930', '330122', '桐庐县', '90', '11');
INSERT INTO `area_info` VALUES ('1196', '350583', '南安市', '122', '13');
INSERT INTO `area_info` VALUES ('993', '330822', '常山县', '97', '11');
INSERT INTO `area_info` VALUES ('609', '220521', '通化县', '57', '7');
INSERT INTO `area_info` VALUES ('2704', '542133', '边坝县', '288', '26');
INSERT INTO `area_info` VALUES ('2267', '510115', '温江区', '241', '23');
INSERT INTO `area_info` VALUES ('2384', '511721', '达　县', '255', '23');
INSERT INTO `area_info` VALUES ('1651', '411521', '罗山县', '169', '16');
INSERT INTO `area_info` VALUES ('1201', '350623', '漳浦县', '123', '13');
INSERT INTO `area_info` VALUES ('518', '210726', '黑山县', '45', '6');
INSERT INTO `area_info` VALUES ('3098', '653121', '疏附县', '340', '31');
INSERT INTO `area_info` VALUES ('2556', '530124', '富民县', '271', '25');
INSERT INTO `area_info` VALUES ('2714', '542231', '隆子县', '289', '26');
INSERT INTO `area_info` VALUES ('1735', '420701', '市辖区', '177', '17');
INSERT INTO `area_info` VALUES ('3133', '654226', '和布克赛尔蒙古自治县', '343', '31');
INSERT INTO `area_info` VALUES ('1367', '370281', '胶州市', '139', '15');
INSERT INTO `area_info` VALUES ('2063', '445281', '普宁市', '219', '19');
INSERT INTO `area_info` VALUES ('1941', '440183', '增城市', '200', '19');
INSERT INTO `area_info` VALUES ('2406', '512021', '安岳县', '258', '23');
INSERT INTO `area_info` VALUES ('661', '230201', '市辖区', '63', '8');
INSERT INTO `area_info` VALUES ('934', '330185', '临安市', '90', '11');
INSERT INTO `area_info` VALUES ('187', '130925', '盐山县', '13', '3');
INSERT INTO `area_info` VALUES ('1703', '420302', '茅箭区', '174', '17');
INSERT INTO `area_info` VALUES ('2332', '511011', '东兴区', '249', '23');
INSERT INTO `area_info` VALUES ('534', '210911', '细河区', '47', '6');
INSERT INTO `area_info` VALUES ('301', '140881', '永济市', '23', '4');
INSERT INTO `area_info` VALUES ('2997', '632522', '同德县', '322', '29');
INSERT INTO `area_info` VALUES ('499', '210423', '清原满族自治县', '42', '6');
INSERT INTO `area_info` VALUES ('1441', '371002', '环翠区', '147', '15');
INSERT INTO `area_info` VALUES ('251', '140411', '郊　区', '19', '4');
INSERT INTO `area_info` VALUES ('482', '210282', '普兰店市', '40', '6');
INSERT INTO `area_info` VALUES ('2712', '542228', '洛扎县', '289', '26');
INSERT INTO `area_info` VALUES ('1241', '360104', '青云谱区', '127', '14');
INSERT INTO `area_info` VALUES ('2770', '610124', '周至县', '294', '27');
INSERT INTO `area_info` VALUES ('1514', '410182', '荥阳市', '155', '16');
INSERT INTO `area_info` VALUES ('2430', '513329', '新龙县', '260', '23');
INSERT INTO `area_info` VALUES ('1309', '360829', '安福县', '134', '14');
INSERT INTO `area_info` VALUES ('2932', '621002', '西峰区', '313', '28');
INSERT INTO `area_info` VALUES ('2719', '542323', '江孜县', '290', '26');
INSERT INTO `area_info` VALUES ('2872', '611023', '商南县', '303', '27');
INSERT INTO `area_info` VALUES ('2478', '520324', '正安县', '264', '24');
INSERT INTO `area_info` VALUES ('3172', '', '花王堂区', '375', '34');
INSERT INTO `area_info` VALUES ('805', '320104', '秦淮区', '77', '10');
INSERT INTO `area_info` VALUES ('1628', '411322', '方城县', '167', '16');
INSERT INTO `area_info` VALUES ('69', '130224', '滦南县', '6', '3');
INSERT INTO `area_info` VALUES ('3049', '650106', '头屯河区', '331', '31');
INSERT INTO `area_info` VALUES ('80', '130321', '青龙满族自治县', '7', '3');
INSERT INTO `area_info` VALUES ('695', '230421', '萝北县', '65', '8');
INSERT INTO `area_info` VALUES ('2161', '451101', '市辖区', '231', '20');
INSERT INTO `area_info` VALUES ('2828', '610629', '洛川县', '299', '27');
INSERT INTO `area_info` VALUES ('1622', '411281', '义马市', '166', '16');
INSERT INTO `area_info` VALUES ('366', '150207', '九原区', '28', '5');
INSERT INTO `area_info` VALUES ('1304', '360824', '新干县', '134', '14');
INSERT INTO `area_info` VALUES ('3135', '654321', '布尔津县', '344', '31');
INSERT INTO `area_info` VALUES ('246', '140311', '郊　区', '18', '4');
INSERT INTO `area_info` VALUES ('1331', '361027', '金溪县', '136', '14');
INSERT INTO `area_info` VALUES ('2092', '450225', '融水苗族自治县', '222', '20');
INSERT INTO `area_info` VALUES ('206', '131081', '霸州市', '14', '3');
INSERT INTO `area_info` VALUES ('542', '211011', '太子河区', '48', '6');
INSERT INTO `area_info` VALUES ('1252', '360281', '乐平市', '128', '14');
INSERT INTO `area_info` VALUES ('1190', '350524', '安溪县', '122', '13');
INSERT INTO `area_info` VALUES ('439', '152202', '阿尔山市', '36', '5');
INSERT INTO `area_info` VALUES ('409', '150724', '鄂温克族自治旗', '33', '5');
INSERT INTO `area_info` VALUES ('3139', '654325', '青河县', '344', '31');
INSERT INTO `area_info` VALUES ('2204', '469007', '东方市', '237', '21');
INSERT INTO `area_info` VALUES ('2535', '522636', '丹寨县', '269', '24');
INSERT INTO `area_info` VALUES ('2179', '451302', '兴宾区', '233', '20');
INSERT INTO `area_info` VALUES ('1974', '440604', '禅城区', '205', '19');
INSERT INTO `area_info` VALUES ('2541', '522726', '独山县', '270', '24');
INSERT INTO `area_info` VALUES ('2800', '610427', '彬　县', '297', '27');
INSERT INTO `area_info` VALUES ('784', '310103', '卢湾区', '367', '9');
INSERT INTO `area_info` VALUES ('403', '150627', '伊金霍洛旗', '32', '5');
INSERT INTO `area_info` VALUES ('345', '141128', '方山县', '26', '4');
INSERT INTO `area_info` VALUES ('2345', '511132', '峨边彝族自治县', '250', '23');
INSERT INTO `area_info` VALUES ('996', '330881', '江山市', '97', '11');
INSERT INTO `area_info` VALUES ('2962', '622924', '广河县', '316', '28');
INSERT INTO `area_info` VALUES ('361', '150202', '东河区', '28', '5');
INSERT INTO `area_info` VALUES ('2906', '620622', '古浪县', '309', '28');
INSERT INTO `area_info` VALUES ('2498', '522225', '思南县', '266', '24');
INSERT INTO `area_info` VALUES ('1781', '421302', '曾都区', '183', '17');
INSERT INTO `area_info` VALUES ('2867', '610929', '白河县', '302', '27');
INSERT INTO `area_info` VALUES ('820', '320205', '锡山区', '78', '10');
INSERT INTO `area_info` VALUES ('2472', '520301', '市辖区', '264', '24');
INSERT INTO `area_info` VALUES ('779', '231283', '海伦市', '73', '8');
INSERT INTO `area_info` VALUES ('2200', '469002', '琼海市', '237', '21');
INSERT INTO `area_info` VALUES ('172', '130821', '承德县', '12', '3');
INSERT INTO `area_info` VALUES ('850', '320506', '吴中区', '81', '10');
INSERT INTO `area_info` VALUES ('1306', '360826', '泰和县', '134', '14');
INSERT INTO `area_info` VALUES ('2970', '623024', '迭部县', '317', '28');
INSERT INTO `area_info` VALUES ('2734', '542338', '岗巴县', '290', '26');
INSERT INTO `area_info` VALUES ('3073', '652722', '精河县', '336', '31');
INSERT INTO `area_info` VALUES ('2913', '620724', '高台县', '310', '28');
INSERT INTO `area_info` VALUES ('1993', '440825', '徐闻县', '207', '19');
INSERT INTO `area_info` VALUES ('138', '130631', '望都县', '10', '3');
INSERT INTO `area_info` VALUES ('2183', '451324', '金秀瑶族自治县', '233', '20');
INSERT INTO `area_info` VALUES ('1957', '440305', '南山区', '202', '19');
INSERT INTO `area_info` VALUES ('1916', '431301', '市辖区', '198', '18');
INSERT INTO `area_info` VALUES ('2412', '513224', '松潘县', '259', '23');
INSERT INTO `area_info` VALUES ('1271', '360481', '瑞昌市', '130', '14');
INSERT INTO `area_info` VALUES ('783', '310101', '黄浦区', '367', '9');
INSERT INTO `area_info` VALUES ('950', '330304', '瓯海区', '92', '11');
INSERT INTO `area_info` VALUES ('1082', '341003', '黄山区', '109', '12');
INSERT INTO `area_info` VALUES ('3016', '632822', '都兰县', '325', '29');
INSERT INTO `area_info` VALUES ('234', '140211', '南郊区', '17', '4');
INSERT INTO `area_info` VALUES ('1571', '410622', '淇　县', '160', '16');
INSERT INTO `area_info` VALUES ('1216', '350781', '邵武市', '124', '13');
INSERT INTO `area_info` VALUES ('380', '150423', '巴林右旗', '30', '5');
INSERT INTO `area_info` VALUES ('64', '130204', '古冶区', '6', '3');
INSERT INTO `area_info` VALUES ('567', '211402', '连山区', '52', '6');
INSERT INTO `area_info` VALUES ('2691', '540125', '堆龙德庆县', '287', '26');
INSERT INTO `area_info` VALUES ('2880', '620105', '安宁区', '304', '28');
INSERT INTO `area_info` VALUES ('2153', '451024', '德保县', '230', '20');
INSERT INTO `area_info` VALUES ('2673', '533102', '瑞丽市', '284', '25');
INSERT INTO `area_info` VALUES ('1585', '410801', '市辖区', '162', '16');
INSERT INTO `area_info` VALUES ('1128', '341621', '涡阳县', '115', '12');
INSERT INTO `area_info` VALUES ('2643', '532526', '弥勒县', '280', '25');
INSERT INTO `area_info` VALUES ('1786', '422823', '巴东县', '184', '17');
INSERT INTO `area_info` VALUES ('1206', '350628', '平和县', '123', '13');
INSERT INTO `area_info` VALUES ('630', '220822', '通榆县', '60', '7');
INSERT INTO `area_info` VALUES ('381', '150424', '林西县', '30', '5');
INSERT INTO `area_info` VALUES ('893', '321001', '市辖区', '86', '10');
INSERT INTO `area_info` VALUES ('2231', '500114', '黔江区', '238', '22');
INSERT INTO `area_info` VALUES ('1486', '371581', '临清市', '152', '15');
INSERT INTO `area_info` VALUES ('2959', '622921', '临夏县', '316', '28');
INSERT INTO `area_info` VALUES ('1662', '411622', '西华县', '170', '16');
INSERT INTO `area_info` VALUES ('2652', '532623', '西畴县', '281', '25');
INSERT INTO `area_info` VALUES ('1052', '340421', '凤台县', '104', '12');
INSERT INTO `area_info` VALUES ('2221', '500104', '大渡口区', '238', '22');
INSERT INTO `area_info` VALUES ('965', '330482', '平湖市', '93', '11');
INSERT INTO `area_info` VALUES ('293', '140823', '闻喜县', '23', '4');
INSERT INTO `area_info` VALUES ('407', '150722', '莫力达瓦达斡尔族自治旗', '33', '5');
INSERT INTO `area_info` VALUES ('1230', '350921', '霞浦县', '126', '13');
INSERT INTO `area_info` VALUES ('1958', '440306', '宝安区', '202', '19');
INSERT INTO `area_info` VALUES ('2801', '610428', '长武县', '297', '27');
INSERT INTO `area_info` VALUES ('2538', '522722', '荔波县', '270', '24');
INSERT INTO `area_info` VALUES ('488', '210311', '千山区', '41', '6');
INSERT INTO `area_info` VALUES ('1507', '410103', '二七区', '155', '16');
INSERT INTO `area_info` VALUES ('1715', '420505', '猇亭区', '175', '17');
INSERT INTO `area_info` VALUES ('3090', '652927', '乌什县', '338', '31');
INSERT INTO `area_info` VALUES ('3177', '', '路填海区', '376', '34');
INSERT INTO `area_info` VALUES ('1519', '410202', '龙亭区', '156', '16');
INSERT INTO `area_info` VALUES ('2362', '511423', '洪雅县', '252', '23');
INSERT INTO `area_info` VALUES ('1720', '420528', '长阳土家族自治县', '175', '17');
INSERT INTO `area_info` VALUES ('1967', '440511', '金平区', '204', '19');
INSERT INTO `area_info` VALUES ('2794', '610404', '渭城区', '297', '27');
INSERT INTO `area_info` VALUES ('2448', '513429', '布拖县', '261', '23');
INSERT INTO `area_info` VALUES ('1025', '340104', '蜀山区', '101', '12');
INSERT INTO `area_info` VALUES ('445', '152502', '锡林浩特市', '37', '5');
INSERT INTO `area_info` VALUES ('2459', '520103', '云岩区', '262', '24');
INSERT INTO `area_info` VALUES ('2585', '530521', '施甸县', '274', '25');
INSERT INTO `area_info` VALUES ('1422', '370811', '任城区', '145', '15');
INSERT INTO `area_info` VALUES ('754', '231004', '爱民区', '71', '8');
INSERT INTO `area_info` VALUES ('516', '210703', '凌河区', '45', '6');
INSERT INTO `area_info` VALUES ('1522', '410205', '南关区', '156', '16');
INSERT INTO `area_info` VALUES ('3166', '', '九龙城区', '373', '33');
INSERT INTO `area_info` VALUES ('884', '320902', '亭湖区', '85', '10');
INSERT INTO `area_info` VALUES ('377', '150404', '松山区', '30', '5');
INSERT INTO `area_info` VALUES ('434', '150927', '察哈尔右翼中旗', '35', '5');
INSERT INTO `area_info` VALUES ('63', '130203', '路北区', '6', '3');
INSERT INTO `area_info` VALUES ('341', '141124', '临　县', '26', '4');
INSERT INTO `area_info` VALUES ('3186', '522501', '市辖区', '380', '24');
INSERT INTO `area_info` VALUES ('1660', '411602', '川汇区', '170', '16');
INSERT INTO `area_info` VALUES ('797', '310117', '松江区', '367', '9');
INSERT INTO `area_info` VALUES ('1569', '410611', '淇滨区', '160', '16');
INSERT INTO `area_info` VALUES ('396', '150602', '东胜区', '32', '5');
INSERT INTO `area_info` VALUES ('2151', '451022', '田东县', '230', '20');
INSERT INTO `area_info` VALUES ('777', '231281', '安达市', '73', '8');
INSERT INTO `area_info` VALUES ('2168', '451221', '南丹县', '232', '20');
INSERT INTO `area_info` VALUES ('1816', '430302', '雨湖区', '188', '18');
INSERT INTO `area_info` VALUES ('774', '231224', '庆安县', '73', '8');
INSERT INTO `area_info` VALUES ('1807', '430203', '芦淞区', '187', '18');
INSERT INTO `area_info` VALUES ('3084', '652901', '阿克苏市', '338', '31');
INSERT INTO `area_info` VALUES ('154', '130706', '下花园区', '11', '3');
INSERT INTO `area_info` VALUES ('2127', '450603', '防城区', '226', '20');
INSERT INTO `area_info` VALUES ('21', '120103', '河西区', '366', '2');
INSERT INTO `area_info` VALUES ('2409', '513221', '汶川县', '259', '23');
INSERT INTO `area_info` VALUES ('544', '211081', '灯塔市', '48', '6');
INSERT INTO `area_info` VALUES ('2079', '450124', '马山县', '221', '20');
INSERT INTO `area_info` VALUES ('724', '230709', '金山屯区', '68', '8');
INSERT INTO `area_info` VALUES ('1364', '370212', '崂山区', '139', '15');
INSERT INTO `area_info` VALUES ('460', '210102', '和平区', '39', '6');
INSERT INTO `area_info` VALUES ('1403', '370684', '蓬莱市', '143', '15');
INSERT INTO `area_info` VALUES ('588', '220211', '丰满区', '54', '7');
INSERT INTO `area_info` VALUES ('2115', '450405', '长洲区', '224', '20');
INSERT INTO `area_info` VALUES ('2878', '620103', '七里河区', '304', '28');
INSERT INTO `area_info` VALUES ('872', '320723', '灌云县', '83', '10');
INSERT INTO `area_info` VALUES ('615', '220602', '八道江区', '58', '7');
INSERT INTO `area_info` VALUES ('68', '130223', '滦　县', '6', '3');
INSERT INTO `area_info` VALUES ('408', '150723', '鄂伦春自治旗', '33', '5');
INSERT INTO `area_info` VALUES ('1404', '370685', '招远市', '143', '15');
INSERT INTO `area_info` VALUES ('163', '130729', '万全县', '11', '3');
INSERT INTO `area_info` VALUES ('82', '130323', '抚宁县', '7', '3');
INSERT INTO `area_info` VALUES ('2515', '522424', '金沙县', '268', '24');
INSERT INTO `area_info` VALUES ('101', '130434', '魏　县', '8', '3');
INSERT INTO `area_info` VALUES ('2493', '520425', '紫云苗族布依族自治县', '265', '24');
INSERT INTO `area_info` VALUES ('719', '230704', '友好区', '68', '8');
INSERT INTO `area_info` VALUES ('1182', '350430', '建宁县', '121', '13');
INSERT INTO `area_info` VALUES ('2371', '511525', '高　县', '253', '23');
INSERT INTO `area_info` VALUES ('53', '130131', '平山县', '5', '3');
INSERT INTO `area_info` VALUES ('1087', '341024', '祁门县', '109', '12');
INSERT INTO `area_info` VALUES ('1431', '370882', '兖州市', '145', '15');
INSERT INTO `area_info` VALUES ('681', '230304', '滴道区', '64', '8');
INSERT INTO `area_info` VALUES ('2989', '632222', '祁连县', '320', '29');
INSERT INTO `area_info` VALUES ('2308', '510704', '游仙区', '246', '23');
INSERT INTO `area_info` VALUES ('1756', '421022', '公安县', '180', '17');
INSERT INTO `area_info` VALUES ('1758', '421024', '江陵县', '180', '17');
INSERT INTO `area_info` VALUES ('1478', '371501', '市辖区', '152', '15');
INSERT INTO `area_info` VALUES ('1823', '430406', '雁峰区', '189', '18');
INSERT INTO `area_info` VALUES ('3091', '652928', '阿瓦提县', '338', '31');
INSERT INTO `area_info` VALUES ('1577', '410721', '新乡县', '161', '16');
INSERT INTO `area_info` VALUES ('1922', '433101', '吉首市', '199', '18');
INSERT INTO `area_info` VALUES ('1678', '411727', '汝南县', '171', '16');
INSERT INTO `area_info` VALUES ('1871', '430822', '桑植县', '193', '18');
INSERT INTO `area_info` VALUES ('3083', '652829', '博湖县', '337', '31');
INSERT INTO `area_info` VALUES ('2552', '530112', '西山区', '271', '25');
INSERT INTO `area_info` VALUES ('1384', '370404', '峄城区', '141', '15');
INSERT INTO `area_info` VALUES ('920', '321324', '泗洪县', '89', '10');
INSERT INTO `area_info` VALUES ('2694', '542121', '昌都县', '288', '26');
INSERT INTO `area_info` VALUES ('1865', '430726', '石门县', '192', '18');
INSERT INTO `area_info` VALUES ('2837', '610724', '西乡县', '300', '27');
INSERT INTO `area_info` VALUES ('127', '130604', '南市区', '10', '3');
INSERT INTO `area_info` VALUES ('1623', '411282', '灵宝市', '166', '16');
INSERT INTO `area_info` VALUES ('728', '230713', '带岭区', '68', '8');
INSERT INTO `area_info` VALUES ('1361', '370203', '市北区', '139', '15');
INSERT INTO `area_info` VALUES ('979', '330683', '嵊州市', '95', '11');
INSERT INTO `area_info` VALUES ('1457', '371321', '沂南县', '150', '15');
INSERT INTO `area_info` VALUES ('1533', '410305', '涧西区', '157', '16');
INSERT INTO `area_info` VALUES ('868', '320705', '新浦区', '83', '10');
INSERT INTO `area_info` VALUES ('974', '330602', '越城区', '95', '11');
INSERT INTO `area_info` VALUES ('650', '230123', '依兰县', '62', '8');
INSERT INTO `area_info` VALUES ('2443', '513424', '德昌县', '261', '23');
INSERT INTO `area_info` VALUES ('2069', '445381', '罗定市', '220', '19');
INSERT INTO `area_info` VALUES ('219', '131182', '深州市', '15', '3');
INSERT INTO `area_info` VALUES ('1938', '440112', '黄埔区', '200', '19');
INSERT INTO `area_info` VALUES ('332', '141033', '蒲　县', '25', '4');
INSERT INTO `area_info` VALUES ('1373', '370302', '淄川区', '140', '15');
INSERT INTO `area_info` VALUES ('1037', '340223', '南陵县', '102', '12');
INSERT INTO `area_info` VALUES ('1599', '410922', '清丰县', '163', '16');
INSERT INTO `area_info` VALUES ('1952', '440281', '乐昌市', '201', '19');
INSERT INTO `area_info` VALUES ('909', '321202', '海陵区', '88', '10');
INSERT INTO `area_info` VALUES ('129', '130622', '清苑县', '10', '3');
INSERT INTO `area_info` VALUES ('1879', '431001', '市辖区', '195', '18');
INSERT INTO `area_info` VALUES ('62', '130202', '路南区', '6', '3');
INSERT INTO `area_info` VALUES ('984', '330726', '浦江县', '96', '11');
INSERT INTO `area_info` VALUES ('2315', '510781', '江油市', '246', '23');
INSERT INTO `area_info` VALUES ('2628', '532322', '双柏县', '279', '25');
INSERT INTO `area_info` VALUES ('1173', '350403', '三元区', '121', '13');
INSERT INTO `area_info` VALUES ('444', '152501', '二连浩特市', '37', '5');
INSERT INTO `area_info` VALUES ('2008', '441224', '怀集县', '209', '19');
INSERT INTO `area_info` VALUES ('1209', '350701', '市辖区', '124', '13');
INSERT INTO `area_info` VALUES ('379', '150422', '巴林左旗', '30', '5');
INSERT INTO `area_info` VALUES ('1459', '371323', '沂水县', '150', '15');
INSERT INTO `area_info` VALUES ('2785', '610326', '眉　县', '296', '27');
INSERT INTO `area_info` VALUES ('3010', '632724', '治多县', '324', '29');
INSERT INTO `area_info` VALUES ('3029', '640301', '市辖区', '328', '30');
INSERT INTO `area_info` VALUES ('2583', '530501', '市辖区', '274', '25');
INSERT INTO `area_info` VALUES ('1810', '430221', '株洲县', '187', '18');
INSERT INTO `area_info` VALUES ('970', '330521', '德清县', '94', '11');
INSERT INTO `area_info` VALUES ('532', '210904', '太平区', '47', '6');
INSERT INTO `area_info` VALUES ('1285', '360725', '崇义县', '133', '14');
INSERT INTO `area_info` VALUES ('679', '230302', '鸡冠区', '64', '8');
INSERT INTO `area_info` VALUES ('1200', '350622', '云霄县', '123', '13');
INSERT INTO `area_info` VALUES ('1902', '431129', '江华瑶族自治县', '196', '18');
INSERT INTO `area_info` VALUES ('697', '230501', '市辖区', '66', '8');
INSERT INTO `area_info` VALUES ('1108', '341321', '砀山县', '112', '12');
INSERT INTO `area_info` VALUES ('2654', '532625', '马关县', '281', '25');
INSERT INTO `area_info` VALUES ('2413', '513225', '九寨沟县', '259', '23');
INSERT INTO `area_info` VALUES ('1716', '420506', '夷陵区', '175', '17');
INSERT INTO `area_info` VALUES ('737', '230804', '前进区', '69', '8');
INSERT INTO `area_info` VALUES ('15', '110116', '怀柔区', '365', '1');
INSERT INTO `area_info` VALUES ('1394', '370601', '市辖区', '143', '15');
INSERT INTO `area_info` VALUES ('1847', '430601', '市辖区', '191', '18');
INSERT INTO `area_info` VALUES ('2532', '522633', '从江县', '269', '24');
INSERT INTO `area_info` VALUES ('142', '130635', '蠡　县', '10', '3');
INSERT INTO `area_info` VALUES ('702', '230521', '集贤县', '66', '8');
INSERT INTO `area_info` VALUES ('224', '140108', '尖草坪区', '16', '4');
INSERT INTO `area_info` VALUES ('2858', '610902', '汉滨区', '302', '27');
INSERT INTO `area_info` VALUES ('1501', '371725', '郓城县', '154', '15');
INSERT INTO `area_info` VALUES ('1110', '341323', '灵璧县', '112', '12');
INSERT INTO `area_info` VALUES ('317', '140981', '原平市', '24', '4');
INSERT INTO `area_info` VALUES ('2548', '530101', '市辖区', '271', '25');
INSERT INTO `area_info` VALUES ('1427', '370830', '汶上县', '145', '15');
INSERT INTO `area_info` VALUES ('2518', '522427', '威宁彝族回族苗族自治县', '268', '24');
INSERT INTO `area_info` VALUES ('2253', '500243', '彭水苗族土家族自治县', '239', '22');
INSERT INTO `area_info` VALUES ('528', '210882', '大石桥市', '46', '6');
INSERT INTO `area_info` VALUES ('176', '130825', '隆化县', '12', '3');
INSERT INTO `area_info` VALUES ('3156', '811000', '上城区', '359', '56');
INSERT INTO `area_info` VALUES ('2296', '510522', '合江县', '244', '23');
INSERT INTO `area_info` VALUES ('2993', '632322', '尖扎县', '321', '29');
INSERT INTO `area_info` VALUES ('3019', '640104', '兴庆区', '326', '30');
INSERT INTO `area_info` VALUES ('1064', '340702', '铜官山区', '107', '12');
INSERT INTO `area_info` VALUES ('2817', '610582', '华阴市', '298', '27');
INSERT INTO `area_info` VALUES ('1398', '370613', '莱山区', '143', '15');
INSERT INTO `area_info` VALUES ('2330', '511001', '市辖区', '249', '23');
INSERT INTO `area_info` VALUES ('1762', '421101', '市辖区', '181', '17');
INSERT INTO `area_info` VALUES ('1631', '411325', '内乡县', '167', '16');
INSERT INTO `area_info` VALUES ('1991', '440811', '麻章区', '207', '19');
INSERT INTO `area_info` VALUES ('466', '210112', '东陵区', '39', '6');
INSERT INTO `area_info` VALUES ('2536', '522701', '都匀市', '270', '24');
INSERT INTO `area_info` VALUES ('276', '140624', '怀仁县', '21', '4');
INSERT INTO `area_info` VALUES ('3104', '653127', '麦盖提县', '340', '31');
INSERT INTO `area_info` VALUES ('2035', '441621', '紫金县', '213', '19');
INSERT INTO `area_info` VALUES ('1665', '411625', '郸城县', '170', '16');
INSERT INTO `area_info` VALUES ('320', '141021', '曲沃县', '25', '4');
INSERT INTO `area_info` VALUES ('766', '231124', '孙吴县', '72', '8');
INSERT INTO `area_info` VALUES ('781', '232722', '塔河县', '74', '8');
INSERT INTO `area_info` VALUES ('721', '230706', '翠峦区', '68', '8');
INSERT INTO `area_info` VALUES ('2533', '522634', '雷山县', '269', '24');
INSERT INTO `area_info` VALUES ('1755', '421003', '荆州区', '180', '17');
INSERT INTO `area_info` VALUES ('338', '141121', '文水县', '26', '4');
INSERT INTO `area_info` VALUES ('1551', '410422', '叶　县', '158', '16');
INSERT INTO `area_info` VALUES ('1913', '431229', '靖州苗族侗族自治县', '197', '18');
INSERT INTO `area_info` VALUES ('2481', '520327', '凤冈县', '264', '24');
INSERT INTO `area_info` VALUES ('2610', '530822', '墨江哈尼族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('433', '150926', '察哈尔右翼前旗', '35', '5');
INSERT INTO `area_info` VALUES ('2909', '620702', '甘州区', '310', '28');
INSERT INTO `area_info` VALUES ('85', '130402', '邯山区', '8', '3');
INSERT INTO `area_info` VALUES ('991', '330802', '柯城区', '97', '11');
INSERT INTO `area_info` VALUES ('2671', '532931', '剑川县', '283', '25');
INSERT INTO `area_info` VALUES ('3138', '654324', '哈巴河县', '344', '31');
INSERT INTO `area_info` VALUES ('2838', '610725', '勉　县', '300', '27');
INSERT INTO `area_info` VALUES ('2276', '510183', '邛崃市', '241', '23');
INSERT INTO `area_info` VALUES ('786', '310105', '长宁区', '367', '9');
INSERT INTO `area_info` VALUES ('877', '320804', '淮阴区', '84', '10');
INSERT INTO `area_info` VALUES ('812', '320115', '江宁区', '77', '10');
INSERT INTO `area_info` VALUES ('1908', '431224', '溆浦县', '197', '18');
INSERT INTO `area_info` VALUES ('2615', '530827', '孟连傣族拉祜族佤族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('3095', '653023', '阿合奇县', '339', '31');
INSERT INTO `area_info` VALUES ('181', '130902', '新华区', '13', '3');
INSERT INTO `area_info` VALUES ('1554', '410481', '舞钢市', '158', '16');
INSERT INTO `area_info` VALUES ('1605', '411002', '魏都区', '164', '16');
INSERT INTO `area_info` VALUES ('1300', '360803', '青原区', '134', '14');
INSERT INTO `area_info` VALUES ('1627', '411321', '南召县', '167', '16');
INSERT INTO `area_info` VALUES ('2904', '620602', '凉州区', '309', '28');
INSERT INTO `area_info` VALUES ('1904', '431202', '鹤城区', '197', '18');
INSERT INTO `area_info` VALUES ('458', '152923', '额济纳旗', '38', '5');
INSERT INTO `area_info` VALUES ('1076', '340826', '宿松县', '108', '12');
INSERT INTO `area_info` VALUES ('2908', '620701', '市辖区', '310', '28');
INSERT INTO `area_info` VALUES ('773', '231223', '青冈县', '73', '8');
INSERT INTO `area_info` VALUES ('314', '140930', '河曲县', '24', '4');
INSERT INTO `area_info` VALUES ('3041', '640502', '沙坡头区', '330', '30');
INSERT INTO `area_info` VALUES ('2999', '632524', '兴海县', '322', '29');
INSERT INTO `area_info` VALUES ('2625', '530926', '耿马傣族佤族自治县', '278', '25');
INSERT INTO `area_info` VALUES ('1483', '371524', '东阿县', '152', '15');
INSERT INTO `area_info` VALUES ('745', '230882', '富锦市', '69', '8');
INSERT INTO `area_info` VALUES ('2196', '460107', '琼山区', '235', '21');
INSERT INTO `area_info` VALUES ('1965', '440501', '市辖区', '204', '19');
INSERT INTO `area_info` VALUES ('2294', '510504', '龙马潭区', '244', '23');
INSERT INTO `area_info` VALUES ('2223', '500106', '沙坪坝区', '238', '22');
INSERT INTO `area_info` VALUES ('2510', '522327', '册亨县', '267', '24');
INSERT INTO `area_info` VALUES ('232', '140202', '城　区', '17', '4');
INSERT INTO `area_info` VALUES ('1780', '421301', '市辖区', '183', '17');
INSERT INTO `area_info` VALUES ('1510', '410106', '上街区', '155', '16');
INSERT INTO `area_info` VALUES ('1008', '331023', '天台县', '99', '11');
INSERT INTO `area_info` VALUES ('3119', '654021', '伊宁县', '342', '31');
INSERT INTO `area_info` VALUES ('1193', '350527', '金门县', '122', '13');
INSERT INTO `area_info` VALUES ('2543', '522728', '罗甸县', '270', '24');
INSERT INTO `area_info` VALUES ('217', '131128', '阜城县', '15', '3');
INSERT INTO `area_info` VALUES ('3097', '653101', '喀什市', '340', '31');
INSERT INTO `area_info` VALUES ('102', '130435', '曲周县', '8', '3');
INSERT INTO `area_info` VALUES ('1891', '431101', '市辖区', '196', '18');
INSERT INTO `area_info` VALUES ('1582', '410728', '长垣县', '161', '16');
INSERT INTO `area_info` VALUES ('2091', '450224', '融安县', '222', '20');
INSERT INTO `area_info` VALUES ('492', '210401', '市辖区', '42', '6');
INSERT INTO `area_info` VALUES ('42', '130107', '井陉矿区', '5', '3');
INSERT INTO `area_info` VALUES ('712', '230621', '肇州县', '67', '8');
INSERT INTO `area_info` VALUES ('1314', '360921', '奉新县', '135', '14');
INSERT INTO `area_info` VALUES ('620', '220681', '临江市', '58', '7');
INSERT INTO `area_info` VALUES ('3085', '652922', '温宿县', '338', '31');
INSERT INTO `area_info` VALUES ('264', '140502', '城　区', '20', '4');
INSERT INTO `area_info` VALUES ('3035', '640402', '原州区', '329', '30');
INSERT INTO `area_info` VALUES ('2742', '542428', '班戈县', '291', '26');
INSERT INTO `area_info` VALUES ('672', '230225', '甘南县', '63', '8');
INSERT INTO `area_info` VALUES ('2958', '622901', '临夏市', '316', '28');
INSERT INTO `area_info` VALUES ('2378', '511621', '岳池县', '254', '23');
INSERT INTO `area_info` VALUES ('1023', '340102', '瑶海区', '101', '12');
INSERT INTO `area_info` VALUES ('626', '220724', '扶余县', '59', '7');
INSERT INTO `area_info` VALUES ('2540', '522725', '瓮安县', '270', '24');
INSERT INTO `area_info` VALUES ('2125', '450601', '市辖区', '226', '20');
INSERT INTO `area_info` VALUES ('1724', '420583', '枝江市', '175', '17');
INSERT INTO `area_info` VALUES ('1468', '371421', '陵　县', '151', '15');
INSERT INTO `area_info` VALUES ('119', '130533', '威　县', '9', '3');
INSERT INTO `area_info` VALUES ('2582', '530428', '元江哈尼族彝族傣族自治县', '273', '25');
INSERT INTO `area_info` VALUES ('1462', '371326', '平邑县', '150', '15');
INSERT INTO `area_info` VALUES ('385', '150429', '宁城县', '30', '5');
INSERT INTO `area_info` VALUES ('2630', '532324', '南华县', '279', '25');
INSERT INTO `area_info` VALUES ('87', '130404', '复兴区', '8', '3');
INSERT INTO `area_info` VALUES ('2890', '620402', '白银区', '307', '28');
INSERT INTO `area_info` VALUES ('538', '211002', '白塔区', '48', '6');
INSERT INTO `area_info` VALUES ('2064', '445301', '市辖区', '220', '19');
INSERT INTO `area_info` VALUES ('614', '220601', '市辖区', '58', '7');
INSERT INTO `area_info` VALUES ('938', '330205', '江北区', '91', '11');
INSERT INTO `area_info` VALUES ('705', '230524', '饶河县', '66', '8');
INSERT INTO `area_info` VALUES ('2387', '511724', '大竹县', '255', '23');
INSERT INTO `area_info` VALUES ('329', '141030', '大宁县', '25', '4');
INSERT INTO `area_info` VALUES ('2418', '513230', '壤塘县', '259', '23');
INSERT INTO `area_info` VALUES ('1455', '371311', '罗庄区', '150', '15');
INSERT INTO `area_info` VALUES ('506', '210522', '桓仁满族自治县', '43', '6');
INSERT INTO `area_info` VALUES ('430', '150923', '商都县', '35', '5');
INSERT INTO `area_info` VALUES ('2964', '622926', '东乡族自治县', '316', '28');
INSERT INTO `area_info` VALUES ('2965', '622927', '积石山保安族东乡族撒拉族自治县', '316', '28');
INSERT INTO `area_info` VALUES ('2265', '510113', '青白江区', '241', '23');
INSERT INTO `area_info` VALUES ('1851', '430621', '岳阳县', '191', '18');
INSERT INTO `area_info` VALUES ('94', '130427', '磁　县', '8', '3');
INSERT INTO `area_info` VALUES ('258', '140428', '长子县', '19', '4');
INSERT INTO `area_info` VALUES ('1840', '430523', '邵阳县', '190', '18');
INSERT INTO `area_info` VALUES ('2847', '610822', '府谷县', '301', '27');
INSERT INTO `area_info` VALUES ('2674', '533103', '潞西市', '284', '25');
INSERT INTO `area_info` VALUES ('1529', '410301', '市辖区', '157', '16');
INSERT INTO `area_info` VALUES ('2621', '530922', '云　县', '278', '25');
INSERT INTO `area_info` VALUES ('2370', '511524', '长宁县', '253', '23');
INSERT INTO `area_info` VALUES ('3122', '654024', '巩留县', '342', '31');
INSERT INTO `area_info` VALUES ('956', '330329', '泰顺县', '92', '11');
INSERT INTO `area_info` VALUES ('898', '321081', '仪征市', '86', '10');
INSERT INTO `area_info` VALUES ('1310', '360830', '永新县', '134', '14');
INSERT INTO `area_info` VALUES ('1267', '360427', '星子县', '130', '14');
INSERT INTO `area_info` VALUES ('2468', '520201', '钟山区', '263', '24');
INSERT INTO `area_info` VALUES ('1680', '411729', '新蔡县', '171', '16');
INSERT INTO `area_info` VALUES ('685', '230321', '鸡东县', '64', '8');
INSERT INTO `area_info` VALUES ('3048', '650105', '水磨沟区', '331', '31');
INSERT INTO `area_info` VALUES ('2225', '500108', '南岸区', '238', '22');
INSERT INTO `area_info` VALUES ('2545', '522730', '龙里县', '270', '24');
INSERT INTO `area_info` VALUES ('2664', '532924', '宾川县', '283', '25');
INSERT INTO `area_info` VALUES ('826', '320302', '鼓楼区', '79', '10');
INSERT INTO `area_info` VALUES ('16', '110117', '平谷区', '365', '1');
INSERT INTO `area_info` VALUES ('2650', '532621', '文山县', '281', '25');
INSERT INTO `area_info` VALUES ('1416', '370783', '寿光市', '144', '15');
INSERT INTO `area_info` VALUES ('2678', '533321', '泸水县', '285', '25');
INSERT INTO `area_info` VALUES ('1246', '360123', '安义县', '127', '14');
INSERT INTO `area_info` VALUES ('1729', '420624', '南漳县', '176', '17');
INSERT INTO `area_info` VALUES ('1621', '411224', '卢氏县', '166', '16');
INSERT INTO `area_info` VALUES ('799', '310119', '南汇区', '367', '9');
INSERT INTO `area_info` VALUES ('1497', '371721', '曹　县', '154', '15');
INSERT INTO `area_info` VALUES ('1253', '360301', '市辖区', '129', '14');
INSERT INTO `area_info` VALUES ('2504', '522301', '兴义市', '267', '24');
INSERT INTO `area_info` VALUES ('848', '320504', '金阊区', '81', '10');
INSERT INTO `area_info` VALUES ('324', '141025', '古　县', '25', '4');
INSERT INTO `area_info` VALUES ('2841', '610728', '镇巴县', '300', '27');
INSERT INTO `area_info` VALUES ('177', '130826', '丰宁满族自治县', '12', '3');
INSERT INTO `area_info` VALUES ('1165', '350301', '市辖区', '120', '13');
INSERT INTO `area_info` VALUES ('571', '211422', '建昌县', '52', '6');
INSERT INTO `area_info` VALUES ('1380', '370323', '沂源县', '140', '15');
INSERT INTO `area_info` VALUES ('2549', '530102', '五华区', '271', '25');
INSERT INTO `area_info` VALUES ('326', '141027', '浮山县', '25', '4');
INSERT INTO `area_info` VALUES ('1154', '350125', '永泰县', '118', '13');
INSERT INTO `area_info` VALUES ('2105', '450326', '永福县', '223', '20');
INSERT INTO `area_info` VALUES ('2873', '611024', '山阳县', '303', '27');
INSERT INTO `area_info` VALUES ('1051', '340406', '潘集区', '104', '12');
INSERT INTO `area_info` VALUES ('1794', '429021', '神农架林区', '185', '17');
INSERT INTO `area_info` VALUES ('2013', '441301', '市辖区', '210', '19');
INSERT INTO `area_info` VALUES ('1739', '420801', '市辖区', '178', '17');
INSERT INTO `area_info` VALUES ('1286', '360726', '安远县', '133', '14');
INSERT INTO `area_info` VALUES ('919', '321323', '泗阳县', '89', '10');
INSERT INTO `area_info` VALUES ('1041', '340304', '禹会区', '103', '12');
INSERT INTO `area_info` VALUES ('2731', '542335', '吉隆县', '290', '26');
INSERT INTO `area_info` VALUES ('1224', '350823', '上杭县', '125', '13');
INSERT INTO `area_info` VALUES ('3131', '654224', '托里县', '343', '31');
INSERT INTO `area_info` VALUES ('1042', '340311', '淮上区', '103', '12');
INSERT INTO `area_info` VALUES ('1479', '371502', '东昌府区', '152', '15');
INSERT INTO `area_info` VALUES ('668', '230208', '梅里斯达斡尔族区', '63', '8');
INSERT INTO `area_info` VALUES ('1453', '371301', '市辖区', '150', '15');
INSERT INTO `area_info` VALUES ('925', '330105', '拱墅区', '90', '11');
INSERT INTO `area_info` VALUES ('1411', '370705', '奎文区', '144', '15');
INSERT INTO `area_info` VALUES ('922', '330102', '上城区', '90', '11');
INSERT INTO `area_info` VALUES ('1864', '430725', '桃源县', '192', '18');
INSERT INTO `area_info` VALUES ('39', '130103', '桥东区', '5', '3');
INSERT INTO `area_info` VALUES ('3134', '654301', '阿勒泰市', '344', '31');
INSERT INTO `area_info` VALUES ('184', '130922', '青　县', '13', '3');
INSERT INTO `area_info` VALUES ('3114', '653225', '策勒县', '341', '31');
INSERT INTO `area_info` VALUES ('1058', '340601', '市辖区', '106', '12');
INSERT INTO `area_info` VALUES ('61', '130201', '市辖区', '6', '3');
INSERT INTO `area_info` VALUES ('659', '230183', '尚志市', '62', '8');
INSERT INTO `area_info` VALUES ('835', '320381', '新沂市', '79', '10');
INSERT INTO `area_info` VALUES ('1855', '430681', '汨罗市', '191', '18');
INSERT INTO `area_info` VALUES ('1264', '360424', '修水县', '130', '14');
INSERT INTO `area_info` VALUES ('926', '330106', '西湖区', '90', '11');
INSERT INTO `area_info` VALUES ('540', '211004', '宏伟区', '48', '6');
INSERT INTO `area_info` VALUES ('450', '152526', '西乌珠穆沁旗', '37', '5');
INSERT INTO `area_info` VALUES ('886', '320921', '响水县', '85', '10');
INSERT INTO `area_info` VALUES ('161', '130727', '阳原县', '11', '3');
INSERT INTO `area_info` VALUES ('2442', '513423', '盐源县', '261', '23');
INSERT INTO `area_info` VALUES ('26', '120108', '汉沽区', '366', '2');
INSERT INTO `area_info` VALUES ('1031', '340202', '镜湖区', '102', '12');
INSERT INTO `area_info` VALUES ('13', '110114', '昌平区', '365', '1');
INSERT INTO `area_info` VALUES ('2565', '530321', '马龙县', '272', '25');
INSERT INTO `area_info` VALUES ('2592', '530622', '巧家县', '275', '25');
INSERT INTO `area_info` VALUES ('229', '140123', '娄烦县', '16', '4');
INSERT INTO `area_info` VALUES ('552', '211204', '清河区', '50', '6');
INSERT INTO `area_info` VALUES ('126', '130603', '北市区', '10', '3');
INSERT INTO `area_info` VALUES ('1929', '433130', '龙山县', '199', '18');
INSERT INTO `area_info` VALUES ('2578', '530424', '华宁县', '273', '25');
INSERT INTO `area_info` VALUES ('2134', '450722', '浦北县', '227', '20');
INSERT INTO `area_info` VALUES ('2979', '630121', '大通回族土族自治县', '318', '29');
INSERT INTO `area_info` VALUES ('1934', '440105', '海珠区', '200', '19');
INSERT INTO `area_info` VALUES ('3160', '', '中西区', '372', '33');
INSERT INTO `area_info` VALUES ('1106', '341301', '市辖区', '112', '12');
INSERT INTO `area_info` VALUES ('2508', '522325', '贞丰县', '267', '24');
INSERT INTO `area_info` VALUES ('231', '140201', '市辖区', '17', '4');
INSERT INTO `area_info` VALUES ('2014', '441302', '惠城区', '210', '19');
INSERT INTO `area_info` VALUES ('1903', '431201', '市辖区', '197', '18');
INSERT INTO `area_info` VALUES ('60', '130185', '鹿泉市', '5', '3');
INSERT INTO `area_info` VALUES ('665', '230205', '昂昂溪区', '63', '8');
INSERT INTO `area_info` VALUES ('2329', '510923', '大英县', '248', '23');
INSERT INTO `area_info` VALUES ('2408', '512081', '简阳市', '258', '23');
INSERT INTO `area_info` VALUES ('2833', '610702', '汉台区', '300', '27');
INSERT INTO `area_info` VALUES ('360', '150201', '市辖区', '28', '5');
INSERT INTO `area_info` VALUES ('2850', '610825', '定边县', '301', '27');
INSERT INTO `area_info` VALUES ('286', '140728', '平遥县', '368', '4');
INSERT INTO `area_info` VALUES ('44', '130121', '井陉县', '5', '3');
INSERT INTO `area_info` VALUES ('1070', '340803', '大观区', '108', '12');
INSERT INTO `area_info` VALUES ('686', '230381', '虎林市', '64', '8');
INSERT INTO `area_info` VALUES ('3170', '', '大埔区', '374', '33');
INSERT INTO `area_info` VALUES ('2733', '542337', '萨嘎县', '290', '26');
INSERT INTO `area_info` VALUES ('285', '140727', '祁　县', '368', '4');
INSERT INTO `area_info` VALUES ('2466', '520123', '修文县', '262', '24');
INSERT INTO `area_info` VALUES ('1528', '410225', '兰考县', '156', '16');
INSERT INTO `area_info` VALUES ('1181', '350429', '泰宁县', '121', '13');
INSERT INTO `area_info` VALUES ('2426', '513325', '雅江县', '260', '23');
INSERT INTO `area_info` VALUES ('1515', '410183', '新密市', '155', '16');
INSERT INTO `area_info` VALUES ('303', '140901', '市辖区', '24', '4');
INSERT INTO `area_info` VALUES ('2320', '510821', '旺苍县', '247', '23');
INSERT INTO `area_info` VALUES ('2343', '511126', '夹江县', '250', '23');
INSERT INTO `area_info` VALUES ('393', '150525', '奈曼旗', '31', '5');
INSERT INTO `area_info` VALUES ('2895', '620501', '市辖区', '308', '28');
INSERT INTO `area_info` VALUES ('1834', '430501', '市辖区', '190', '18');
INSERT INTO `area_info` VALUES ('1611', '411101', '市辖区', '165', '16');
INSERT INTO `area_info` VALUES ('17', '110228', '密云县', '365', '1');
INSERT INTO `area_info` VALUES ('2788', '610329', '麟游县', '296', '27');
INSERT INTO `area_info` VALUES ('2571', '530328', '沾益县', '272', '25');
INSERT INTO `area_info` VALUES ('749', '230904', '茄子河区', '70', '8');
INSERT INTO `area_info` VALUES ('908', '321201', '市辖区', '88', '10');
INSERT INTO `area_info` VALUES ('2727', '542331', '康马县', '290', '26');
INSERT INTO `area_info` VALUES ('2349', '511302', '顺庆区', '251', '23');
INSERT INTO `area_info` VALUES ('2521', '522622', '黄平县', '269', '24');
INSERT INTO `area_info` VALUES ('703', '230522', '友谊县', '66', '8');
INSERT INTO `area_info` VALUES ('2760', '610102', '新城区', '294', '27');
INSERT INTO `area_info` VALUES ('2743', '542429', '巴青县', '291', '26');
INSERT INTO `area_info` VALUES ('2864', '610926', '平利县', '302', '27');
INSERT INTO `area_info` VALUES ('271', '140602', '朔城区', '21', '4');
INSERT INTO `area_info` VALUES ('722', '230707', '新青区', '68', '8');
INSERT INTO `area_info` VALUES ('501', '210502', '平山区', '43', '6');
INSERT INTO `area_info` VALUES ('1371', '370285', '莱西市', '139', '15');
INSERT INTO `area_info` VALUES ('575', '220103', '宽城区', '53', '7');
INSERT INTO `area_info` VALUES ('632', '220882', '大安市', '60', '7');
INSERT INTO `area_info` VALUES ('2027', '441481', '兴宁市', '211', '19');
INSERT INTO `area_info` VALUES ('990', '330801', '市辖区', '97', '11');
INSERT INTO `area_info` VALUES ('2745', '542521', '普兰县', '292', '26');
INSERT INTO `area_info` VALUES ('3047', '650104', '新市区', '331', '31');
INSERT INTO `area_info` VALUES ('359', '150125', '武川县', '27', '5');
INSERT INTO `area_info` VALUES ('2844', '610801', '市辖区', '301', '27');
INSERT INTO `area_info` VALUES ('3069', '652325', '奇台县', '335', '31');
INSERT INTO `area_info` VALUES ('72', '130229', '玉田县', '6', '3');
INSERT INTO `area_info` VALUES ('1712', '420502', '西陵区', '175', '17');
INSERT INTO `area_info` VALUES ('116', '130530', '新河县', '9', '3');
INSERT INTO `area_info` VALUES ('311', '140927', '神池县', '24', '4');
INSERT INTO `area_info` VALUES ('1402', '370683', '莱州市', '143', '15');
INSERT INTO `area_info` VALUES ('3072', '652701', '博乐市', '336', '31');
INSERT INTO `area_info` VALUES ('1763', '421102', '黄州区', '181', '17');
INSERT INTO `area_info` VALUES ('2446', '513427', '宁南县', '261', '23');
INSERT INTO `area_info` VALUES ('2761', '610103', '碑林区', '294', '27');
INSERT INTO `area_info` VALUES ('1480', '371521', '阳谷县', '152', '15');
INSERT INTO `area_info` VALUES ('1520', '410203', '顺河回族区', '156', '16');
INSERT INTO `area_info` VALUES ('892', '320982', '大丰市', '85', '10');
INSERT INTO `area_info` VALUES ('1953', '440282', '南雄市', '201', '19');
INSERT INTO `area_info` VALUES ('2885', '620201', '市辖区', '305', '28');
INSERT INTO `area_info` VALUES ('1596', '410883', '孟州市', '162', '16');
INSERT INTO `area_info` VALUES ('832', '320322', '沛　县', '79', '10');
INSERT INTO `area_info` VALUES ('387', '150501', '市辖区', '31', '5');
INSERT INTO `area_info` VALUES ('10', '110111', '房山区', '365', '1');
INSERT INTO `area_info` VALUES ('2806', '610501', '市辖区', '298', '27');
INSERT INTO `area_info` VALUES ('1347', '361181', '德兴市', '137', '14');
INSERT INTO `area_info` VALUES ('1033', '340204', '新芜区', '102', '12');
INSERT INTO `area_info` VALUES ('1145', '350102', '鼓楼区', '118', '13');
INSERT INTO `area_info` VALUES ('1461', '371325', '费　县', '150', '15');
INSERT INTO `area_info` VALUES ('2374', '511528', '兴文县', '253', '23');
INSERT INTO `area_info` VALUES ('1297', '360782', '南康市', '133', '14');
INSERT INTO `area_info` VALUES ('2843', '610730', '佛坪县', '300', '27');
INSERT INTO `area_info` VALUES ('2774', '610202', '王益区', '295', '27');
INSERT INTO `area_info` VALUES ('1287', '360727', '龙南县', '133', '14');
INSERT INTO `area_info` VALUES ('904', '321112', '丹徒区', '87', '10');
INSERT INTO `area_info` VALUES ('771', '231221', '望奎县', '73', '8');
INSERT INTO `area_info` VALUES ('1489', '371621', '惠民县', '153', '15');
INSERT INTO `area_info` VALUES ('1038', '340301', '市辖区', '103', '12');
INSERT INTO `area_info` VALUES ('429', '150922', '化德县', '35', '5');
INSERT INTO `area_info` VALUES ('2235', '500224', '铜梁县', '239', '22');
INSERT INTO `area_info` VALUES ('1909', '431225', '会同县', '197', '18');
INSERT INTO `area_info` VALUES ('1697', '420203', '西塞山区', '173', '17');
INSERT INTO `area_info` VALUES ('882', '320831', '金湖县', '84', '10');
INSERT INTO `area_info` VALUES ('699', '230503', '岭东区', '66', '8');
INSERT INTO `area_info` VALUES ('740', '230822', '桦南县', '69', '8');
INSERT INTO `area_info` VALUES ('3190', '330922', '市辖区', '384', '17');
INSERT INTO `area_info` VALUES ('2292', '510502', '江阳区', '244', '23');
INSERT INTO `area_info` VALUES ('2419', '513231', '阿坝县', '259', '23');
INSERT INTO `area_info` VALUES ('813', '320116', '六合区', '77', '10');
INSERT INTO `area_info` VALUES ('2023', '441423', '丰顺县', '211', '19');
INSERT INTO `area_info` VALUES ('1163', '350212', '同安区', '119', '13');
INSERT INTO `area_info` VALUES ('1115', '341422', '无为县', '113', '12');
INSERT INTO `area_info` VALUES ('693', '230406', '东山区', '65', '8');
INSERT INTO `area_info` VALUES ('1415', '370782', '诸城市', '144', '15');
INSERT INTO `area_info` VALUES ('106', '130503', '桥西区', '9', '3');
INSERT INTO `area_info` VALUES ('12', '110113', '顺义区', '365', '1');
INSERT INTO `area_info` VALUES ('239', '140224', '灵丘县', '17', '4');
INSERT INTO `area_info` VALUES ('2542', '522727', '平塘县', '270', '24');
INSERT INTO `area_info` VALUES ('1368', '370282', '即墨市', '139', '15');
INSERT INTO `area_info` VALUES ('1204', '350626', '东山县', '123', '13');
INSERT INTO `area_info` VALUES ('169', '130802', '双桥区', '12', '3');
INSERT INTO `area_info` VALUES ('3194', '631000', '市辖区', '388', '25');
INSERT INTO `area_info` VALUES ('3000', '632525', '贵南县', '322', '29');
INSERT INTO `area_info` VALUES ('1159', '350203', '思明区', '119', '13');
INSERT INTO `area_info` VALUES ('1107', '341302', '墉桥区', '112', '12');
INSERT INTO `area_info` VALUES ('553', '211221', '铁岭县', '50', '6');
INSERT INTO `area_info` VALUES ('811', '320114', '雨花台区', '77', '10');
INSERT INTO `area_info` VALUES ('1388', '370501', '市辖区', '142', '15');
INSERT INTO `area_info` VALUES ('1170', '350322', '仙游县', '120', '13');
INSERT INTO `area_info` VALUES ('1352', '370105', '天桥区', '138', '15');
INSERT INTO `area_info` VALUES ('2938', '621026', '宁　县', '313', '28');
INSERT INTO `area_info` VALUES ('1191', '350525', '永春县', '122', '13');
INSERT INTO `area_info` VALUES ('1639', '411402', '梁园区', '168', '16');
INSERT INTO `area_info` VALUES ('2260', '510105', '青羊区', '241', '23');
INSERT INTO `area_info` VALUES ('2502', '522229', '松桃苗族自治县', '266', '24');
INSERT INTO `area_info` VALUES ('2874', '611025', '镇安县', '303', '27');
INSERT INTO `area_info` VALUES ('1636', '411330', '桐柏县', '167', '16');
INSERT INTO `area_info` VALUES ('2377', '511602', '广安区', '254', '23');
INSERT INTO `area_info` VALUES ('1784', '422802', '利川市', '184', '17');
INSERT INTO `area_info` VALUES ('2646', '532529', '红河县', '280', '25');
INSERT INTO `area_info` VALUES ('851', '320507', '相城区', '81', '10');
INSERT INTO `area_info` VALUES ('1494', '371626', '邹平县', '153', '15');
INSERT INTO `area_info` VALUES ('2100', '450321', '阳朔县', '223', '20');
INSERT INTO `area_info` VALUES ('2055', '445102', '湘桥区', '218', '19');
INSERT INTO `area_info` VALUES ('1495', '371701', '市辖区', '154', '15');
INSERT INTO `area_info` VALUES ('2663', '532923', '祥云县', '283', '25');
INSERT INTO `area_info` VALUES ('2181', '451322', '象州县', '233', '20');
INSERT INTO `area_info` VALUES ('1100', '341204', '颍泉区', '111', '12');
INSERT INTO `area_info` VALUES ('597', '220322', '梨树县', '55', '7');
INSERT INTO `area_info` VALUES ('680', '230303', '恒山区', '64', '8');
INSERT INTO `area_info` VALUES ('2725', '542329', '白朗县', '290', '26');
INSERT INTO `area_info` VALUES ('2721', '542325', '萨迦县', '290', '26');
INSERT INTO `area_info` VALUES ('1330', '361026', '宜黄县', '136', '14');
INSERT INTO `area_info` VALUES ('1157', '350182', '长乐市', '118', '13');
INSERT INTO `area_info` VALUES ('2339', '511112', '五通桥区', '250', '23');
INSERT INTO `area_info` VALUES ('2039', '441625', '东源县', '213', '19');
INSERT INTO `area_info` VALUES ('1721', '420529', '五峰土家族自治县', '175', '17');
INSERT INTO `area_info` VALUES ('2398', '511827', '宝兴县', '256', '23');
INSERT INTO `area_info` VALUES ('2499', '522226', '印江土家族苗族自治县', '266', '24');
INSERT INTO `area_info` VALUES ('1923', '433122', '泸溪县', '199', '18');
INSERT INTO `area_info` VALUES ('209', '131102', '桃城区', '15', '3');
INSERT INTO `area_info` VALUES ('2675', '533122', '梁河县', '284', '25');
INSERT INTO `area_info` VALUES ('1857', '430701', '市辖区', '192', '18');
INSERT INTO `area_info` VALUES ('2457', '520101', '市辖区', '262', '24');
INSERT INTO `area_info` VALUES ('3164', '', '油尖旺区', '373', '33');
INSERT INTO `area_info` VALUES ('1512', '410122', '中牟县', '155', '16');
INSERT INTO `area_info` VALUES ('1425', '370828', '金乡县', '145', '15');
INSERT INTO `area_info` VALUES ('1272', '360501', '市辖区', '131', '14');
INSERT INTO `area_info` VALUES ('2494', '522201', '铜仁市', '266', '24');
INSERT INTO `area_info` VALUES ('912', '321282', '靖江市', '88', '10');
INSERT INTO `area_info` VALUES ('368', '150222', '固阳县', '28', '5');
INSERT INTO `area_info` VALUES ('3162', '', '东区', '372', '33');
INSERT INTO `area_info` VALUES ('809', '320111', '浦口区', '77', '10');
INSERT INTO `area_info` VALUES ('2241', '500230', '丰都县', '239', '22');
INSERT INTO `area_info` VALUES ('1027', '340121', '长丰县', '101', '12');
INSERT INTO `area_info` VALUES ('57', '130182', '藁城市', '5', '3');
INSERT INTO `area_info` VALUES ('1321', '360982', '樟树市', '135', '14');
INSERT INTO `area_info` VALUES ('1523', '410211', '郊　区', '156', '16');
INSERT INTO `area_info` VALUES ('1485', '371526', '高唐县', '152', '15');
INSERT INTO `area_info` VALUES ('2031', '441523', '陆河县', '212', '19');
INSERT INTO `area_info` VALUES ('2676', '533123', '盈江县', '284', '25');
INSERT INTO `area_info` VALUES ('2073', '450105', '江南区', '221', '20');
INSERT INTO `area_info` VALUES ('1858', '430702', '武陵区', '192', '18');
INSERT INTO `area_info` VALUES ('2862', '610924', '紫阳县', '302', '27');
INSERT INTO `area_info` VALUES ('1750', '420981', '应城市', '179', '17');
INSERT INTO `area_info` VALUES ('327', '141028', '吉　县', '25', '4');
INSERT INTO `area_info` VALUES ('27', '120109', '大港区', '366', '2');
INSERT INTO `area_info` VALUES ('333', '141034', '汾西县', '25', '4');
INSERT INTO `area_info` VALUES ('1920', '431381', '冷水江市', '198', '18');
INSERT INTO `area_info` VALUES ('1024', '340103', '庐阳区', '101', '12');
INSERT INTO `area_info` VALUES ('529', '210901', '市辖区', '47', '6');
INSERT INTO `area_info` VALUES ('1994', '440881', '廉江市', '207', '19');
INSERT INTO `area_info` VALUES ('836', '320382', '邳州市', '79', '10');
INSERT INTO `area_info` VALUES ('2686', '540102', '城关区', '287', '26');
INSERT INTO `area_info` VALUES ('2270', '510124', '郫　县', '241', '23');
INSERT INTO `area_info` VALUES ('70', '130225', '乐亭县', '6', '3');
INSERT INTO `area_info` VALUES ('2076', '450109', '邕宁区', '221', '20');
INSERT INTO `area_info` VALUES ('436', '150929', '四子王旗', '35', '5');
INSERT INTO `area_info` VALUES ('2453', '513434', '越西县', '261', '23');
INSERT INTO `area_info` VALUES ('2860', '610922', '石泉县', '302', '27');
INSERT INTO `area_info` VALUES ('3123', '654025', '新源县', '342', '31');
INSERT INTO `area_info` VALUES ('99', '130432', '广平县', '8', '3');
INSERT INTO `area_info` VALUES ('1705', '420321', '郧　县', '174', '17');
INSERT INTO `area_info` VALUES ('2195', '460106', '龙华区', '235', '21');
INSERT INTO `area_info` VALUES ('1077', '340827', '望江县', '108', '12');
INSERT INTO `area_info` VALUES ('2182', '451323', '武宣县', '233', '20');
INSERT INTO `area_info` VALUES ('1248', '360201', '市辖区', '128', '14');
INSERT INTO `area_info` VALUES ('1026', '340111', '包河区', '101', '12');
INSERT INTO `area_info` VALUES ('4', '110104', '宣武区', '365', '1');
INSERT INTO `area_info` VALUES ('2710', '542226', '曲松县', '289', '26');
INSERT INTO `area_info` VALUES ('744', '230881', '同江市', '69', '8');
INSERT INTO `area_info` VALUES ('1372', '370301', '市辖区', '140', '15');
INSERT INTO `area_info` VALUES ('2960', '622922', '康乐县', '316', '28');
INSERT INTO `area_info` VALUES ('1726', '420602', '襄城区', '176', '17');
INSERT INTO `area_info` VALUES ('2603', '530721', '玉龙纳西族自治县', '276', '25');
INSERT INTO `area_info` VALUES ('897', '321023', '宝应县', '86', '10');
INSERT INTO `area_info` VALUES ('521', '210782', '北宁市', '45', '6');
INSERT INTO `area_info` VALUES ('438', '152201', '乌兰浩特市', '36', '5');
INSERT INTO `area_info` VALUES ('1442', '371081', '文登市', '147', '15');
INSERT INTO `area_info` VALUES ('2072', '450103', '青秀区', '221', '20');
INSERT INTO `area_info` VALUES ('1517', '410185', '登封市', '155', '16');
INSERT INTO `area_info` VALUES ('1317', '360924', '宜丰县', '135', '14');
INSERT INTO `area_info` VALUES ('2030', '441521', '海丰县', '212', '19');
INSERT INTO `area_info` VALUES ('1066', '340711', '郊　区', '107', '12');
INSERT INTO `area_info` VALUES ('862', '320681', '启东市', '82', '10');
INSERT INTO `area_info` VALUES ('1292', '360732', '兴国县', '133', '14');
INSERT INTO `area_info` VALUES ('1844', '430528', '新宁县', '190', '18');
INSERT INTO `area_info` VALUES ('1939', '440113', '番禺区', '200', '19');
INSERT INTO `area_info` VALUES ('268', '140525', '泽州县', '20', '4');
INSERT INTO `area_info` VALUES ('1336', '361102', '信州区', '137', '14');
INSERT INTO `area_info` VALUES ('1984', '440783', '开平市', '206', '19');
INSERT INTO `area_info` VALUES ('34', '120221', '宁河县', '366', '2');
INSERT INTO `area_info` VALUES ('1222', '350821', '长汀县', '125', '13');
INSERT INTO `area_info` VALUES ('2752', '542621', '林芝县', '293', '26');
INSERT INTO `area_info` VALUES ('1741', '420804', '掇刀区', '178', '17');
INSERT INTO `area_info` VALUES ('2323', '510824', '苍溪县', '247', '23');
INSERT INTO `area_info` VALUES ('1742', '420821', '京山县', '178', '17');
INSERT INTO `area_info` VALUES ('1988', '440802', '赤坎区', '207', '19');
INSERT INTO `area_info` VALUES ('2175', '451228', '都安瑶族自治县', '232', '20');
INSERT INTO `area_info` VALUES ('3121', '654023', '霍城县', '342', '31');
INSERT INTO `area_info` VALUES ('2372', '511526', '珙　县', '253', '23');
INSERT INTO `area_info` VALUES ('1211', '350721', '顺昌县', '124', '13');
INSERT INTO `area_info` VALUES ('1513', '410181', '巩义市', '155', '16');
INSERT INTO `area_info` VALUES ('20', '120102', '河东区', '366', '2');
INSERT INTO `area_info` VALUES ('2948', '621201', '市辖区', '315', '28');
INSERT INTO `area_info` VALUES ('1227', '350881', '漳平市', '125', '13');
INSERT INTO `area_info` VALUES ('2239', '500228', '梁平县', '239', '22');
INSERT INTO `area_info` VALUES ('1469', '371422', '宁津县', '151', '15');
INSERT INTO `area_info` VALUES ('778', '231282', '肇东市', '73', '8');
INSERT INTO `area_info` VALUES ('2421', '513233', '红原县', '259', '23');
INSERT INTO `area_info` VALUES ('2159', '451030', '西林县', '230', '20');
INSERT INTO `area_info` VALUES ('585', '220202', '昌邑区', '54', '7');
INSERT INTO `area_info` VALUES ('2739', '542425', '安多县', '291', '26');
INSERT INTO `area_info` VALUES ('322', '141023', '襄汾县', '25', '4');
INSERT INTO `area_info` VALUES ('1972', '440523', '南澳县', '204', '19');
INSERT INTO `area_info` VALUES ('1172', '350402', '梅列区', '121', '13');
INSERT INTO `area_info` VALUES ('1460', '371324', '苍山县', '150', '15');
INSERT INTO `area_info` VALUES ('2807', '610502', '临渭区', '298', '27');
INSERT INTO `area_info` VALUES ('120', '130534', '清河县', '9', '3');
INSERT INTO `area_info` VALUES ('1370', '370284', '胶南市', '139', '15');
INSERT INTO `area_info` VALUES ('1541', '410327', '宜阳县', '157', '16');
INSERT INTO `area_info` VALUES ('2647', '532530', '金平苗族瑶族傣族自治县', '280', '25');
INSERT INTO `area_info` VALUES ('2303', '510681', '广汉市', '245', '23');
INSERT INTO `area_info` VALUES ('1323', '361001', '市辖区', '136', '14');
INSERT INTO `area_info` VALUES ('587', '220204', '船营区', '54', '7');
INSERT INTO `area_info` VALUES ('18', '110229', '延庆县', '365', '1');
INSERT INTO `area_info` VALUES ('2509', '522326', '望谟县', '267', '24');
INSERT INTO `area_info` VALUES ('2835', '610722', '城固县', '300', '27');
INSERT INTO `area_info` VALUES ('1949', '440229', '翁源县', '201', '19');
INSERT INTO `area_info` VALUES ('1155', '350128', '平潭县', '118', '13');
INSERT INTO `area_info` VALUES ('2644', '532527', '泸西县', '280', '25');
INSERT INTO `area_info` VALUES ('1950', '440232', '乳源瑶族自治县', '201', '19');
INSERT INTO `area_info` VALUES ('3021', '640106', '金凤区', '326', '30');
INSERT INTO `area_info` VALUES ('405', '150702', '海拉尔区', '33', '5');
INSERT INTO `area_info` VALUES ('502', '210503', '溪湖区', '43', '6');
INSERT INTO `area_info` VALUES ('373', '150304', '乌达区', '29', '5');
INSERT INTO `area_info` VALUES ('953', '330326', '平阳县', '92', '11');
INSERT INTO `area_info` VALUES ('2123', '450512', '铁山港区', '225', '20');
INSERT INTO `area_info` VALUES ('2639', '532522', '蒙自县', '280', '25');
INSERT INTO `area_info` VALUES ('1213', '350723', '光泽县', '124', '13');
INSERT INTO `area_info` VALUES ('1669', '411681', '项城市', '170', '16');
INSERT INTO `area_info` VALUES ('1374', '370303', '张店区', '140', '15');
INSERT INTO `area_info` VALUES ('875', '320802', '清河区', '84', '10');
INSERT INTO `area_info` VALUES ('944', '330281', '余姚市', '91', '11');
INSERT INTO `area_info` VALUES ('1085', '341022', '休宁县', '109', '12');
INSERT INTO `area_info` VALUES ('2715', '542232', '错那县', '289', '26');
INSERT INTO `area_info` VALUES ('1711', '420501', '市辖区', '175', '17');
INSERT INTO `area_info` VALUES ('1830', '430424', '衡东县', '189', '18');
INSERT INTO `area_info` VALUES ('941', '330212', '鄞州区', '91', '11');
INSERT INTO `area_info` VALUES ('2423', '513322', '泸定县', '260', '23');
INSERT INTO `area_info` VALUES ('130', '130623', '涞水县', '10', '3');
INSERT INTO `area_info` VALUES ('2165', '451123', '富川瑶族自治县', '231', '20');
INSERT INTO `area_info` VALUES ('1017', '331124', '松阳县', '100', '11');
INSERT INTO `area_info` VALUES ('3080', '652826', '焉耆回族自治县', '337', '31');
INSERT INTO `area_info` VALUES ('2662', '532922', '漾濞彝族自治县', '283', '25');
INSERT INTO `area_info` VALUES ('536', '210922', '彰武县', '47', '6');
INSERT INTO `area_info` VALUES ('1798', '430104', '岳麓区', '186', '18');
INSERT INTO `area_info` VALUES ('887', '320922', '滨海县', '85', '10');
INSERT INTO `area_info` VALUES ('651', '230124', '方正县', '62', '8');
INSERT INTO `area_info` VALUES ('1926', '433125', '保靖县', '199', '18');
INSERT INTO `area_info` VALUES ('2584', '530502', '隆阳区', '274', '25');
INSERT INTO `area_info` VALUES ('2563', '530301', '市辖区', '272', '25');
INSERT INTO `area_info` VALUES ('1987', '440801', '市辖区', '207', '19');
INSERT INTO `area_info` VALUES ('961', '330411', '秀洲区', '93', '11');
INSERT INTO `area_info` VALUES ('2163', '451121', '昭平县', '231', '20');
INSERT INTO `area_info` VALUES ('2034', '441602', '源城区', '213', '19');
INSERT INTO `area_info` VALUES ('2877', '620102', '城关区', '304', '28');
INSERT INTO `area_info` VALUES ('2396', '511825', '天全县', '256', '23');
INSERT INTO `area_info` VALUES ('1505', '410101', '市辖区', '155', '16');
INSERT INTO `area_info` VALUES ('916', '321302', '宿城区', '89', '10');
INSERT INTO `area_info` VALUES ('1942', '440184', '从化市', '200', '19');
INSERT INTO `area_info` VALUES ('2897', '620503', '北道区', '308', '28');
INSERT INTO `area_info` VALUES ('2580', '530426', '峨山彝族自治县', '273', '25');
INSERT INTO `area_info` VALUES ('1773', '421201', '市辖区', '182', '17');
INSERT INTO `area_info` VALUES ('1498', '371722', '单　县', '154', '15');
INSERT INTO `area_info` VALUES ('2940', '621101', '市辖区', '314', '28');
INSERT INTO `area_info` VALUES ('3082', '652828', '和硕县', '337', '31');
INSERT INTO `area_info` VALUES ('3105', '653128', '岳普湖县', '340', '31');
INSERT INTO `area_info` VALUES ('448', '152524', '苏尼特右旗', '37', '5');
INSERT INTO `area_info` VALUES ('2834', '610721', '南郑县', '300', '27');
INSERT INTO `area_info` VALUES ('25', '120107', '塘沽区', '366', '2');
INSERT INTO `area_info` VALUES ('3117', '654002', '伊宁市', '342', '31');
INSERT INTO `area_info` VALUES ('2336', '511101', '市辖区', '250', '23');
INSERT INTO `area_info` VALUES ('2433', '513332', '石渠县', '260', '23');
INSERT INTO `area_info` VALUES ('519', '210727', '义　县', '45', '6');
INSERT INTO `area_info` VALUES ('910', '321203', '高港区', '88', '10');
INSERT INTO `area_info` VALUES ('1966', '440507', '龙湖区', '204', '19');
INSERT INTO `area_info` VALUES ('1150', '350121', '闽侯县', '118', '13');
INSERT INTO `area_info` VALUES ('81', '130322', '昌黎县', '7', '3');
INSERT INTO `area_info` VALUES ('1659', '411601', '市辖区', '170', '16');
INSERT INTO `area_info` VALUES ('195', '130983', '黄骅市', '13', '3');
INSERT INTO `area_info` VALUES ('798', '310118', '青浦区', '367', '9');
INSERT INTO `area_info` VALUES ('1518', '410201', '市辖区', '156', '16');
INSERT INTO `area_info` VALUES ('3165', '', '深水㘵区', '373', '33');
INSERT INTO `area_info` VALUES ('1274', '360521', '分宜县', '131', '14');
INSERT INTO `area_info` VALUES ('2577', '530423', '通海县', '273', '25');
INSERT INTO `area_info` VALUES ('93', '130426', '涉　县', '8', '3');
INSERT INTO `area_info` VALUES ('88', '130406', '峰峰矿区', '8', '3');
INSERT INTO `area_info` VALUES ('2799', '610426', '永寿县', '297', '27');
INSERT INTO `area_info` VALUES ('734', '230801', '市辖区', '69', '8');
INSERT INTO `area_info` VALUES ('3187', '522601', '市辖区', '381', '24');
INSERT INTO `area_info` VALUES ('1382', '370402', '市中区', '141', '15');
INSERT INTO `area_info` VALUES ('3143', '659003', '图木舒克市', '345', '31');
INSERT INTO `area_info` VALUES ('490', '210323', '岫岩满族自治县', '41', '6');
INSERT INTO `area_info` VALUES ('2570', '530326', '会泽县', '272', '25');
INSERT INTO `area_info` VALUES ('806', '320105', '建邺区', '77', '10');
INSERT INTO `area_info` VALUES ('671', '230224', '泰来县', '63', '8');
INSERT INTO `area_info` VALUES ('556', '211281', '调兵山市', '50', '6');
INSERT INTO `area_info` VALUES ('2366', '511502', '翠屏区', '253', '23');
INSERT INTO `area_info` VALUES ('1607', '411024', '鄢陵县', '164', '16');
INSERT INTO `area_info` VALUES ('2526', '522627', '天柱县', '269', '24');
INSERT INTO `area_info` VALUES ('2427', '513326', '道孚县', '260', '23');
INSERT INTO `area_info` VALUES ('1876', '430922', '桃江县', '194', '18');
INSERT INTO `area_info` VALUES ('1369', '370283', '平度市', '139', '15');
INSERT INTO `area_info` VALUES ('2641', '532524', '建水县', '280', '25');
INSERT INTO `area_info` VALUES ('1236', '350981', '福安市', '126', '13');
INSERT INTO `area_info` VALUES ('131', '130624', '阜平县', '10', '3');
INSERT INTO `area_info` VALUES ('1240', '360103', '西湖区', '127', '14');
INSERT INTO `area_info` VALUES ('2202', '469005', '文昌市', '237', '21');
INSERT INTO `area_info` VALUES ('2976', '630103', '城中区', '318', '29');
INSERT INTO `area_info` VALUES ('104', '130501', '市辖区', '9', '3');
INSERT INTO `area_info` VALUES ('435', '150928', '察哈尔右翼后旗', '35', '5');
INSERT INTO `area_info` VALUES ('2483', '520329', '余庆县', '264', '24');
INSERT INTO `area_info` VALUES ('1504', '371728', '东明县', '154', '15');
INSERT INTO `area_info` VALUES ('548', '211121', '大洼县', '49', '6');
INSERT INTO `area_info` VALUES ('2028', '441501', '市辖区', '212', '19');
INSERT INTO `area_info` VALUES ('1242', '360105', '湾里区', '127', '14');
INSERT INTO `area_info` VALUES ('1386', '370406', '山亭区', '141', '15');
INSERT INTO `area_info` VALUES ('1552', '410423', '鲁山县', '158', '16');
INSERT INTO `area_info` VALUES ('1277', '360622', '余江县', '132', '14');
INSERT INTO `area_info` VALUES ('480', '210224', '长海县', '40', '6');
INSERT INTO `area_info` VALUES ('5', '110105', '朝阳区', '365', '1');
INSERT INTO `area_info` VALUES ('2573', '530401', '市辖区', '273', '25');
INSERT INTO `area_info` VALUES ('1356', '370125', '济阳县', '138', '15');
INSERT INTO `area_info` VALUES ('3008', '632722', '杂多县', '324', '29');
INSERT INTO `area_info` VALUES ('2099', '450311', '雁山区', '223', '20');
INSERT INTO `area_info` VALUES ('1850', '430611', '君山区', '191', '18');
INSERT INTO `area_info` VALUES ('440', '152221', '科尔沁右翼前旗', '36', '5');
INSERT INTO `area_info` VALUES ('2741', '542427', '索　县', '291', '26');
INSERT INTO `area_info` VALUES ('140', '130633', '易　县', '10', '3');
INSERT INTO `area_info` VALUES ('2101', '450322', '临桂县', '223', '20');
INSERT INTO `area_info` VALUES ('1195', '350582', '晋江市', '122', '13');
INSERT INTO `area_info` VALUES ('2889', '620401', '市辖区', '307', '28');
INSERT INTO `area_info` VALUES ('2804', '610431', '武功县', '297', '27');
INSERT INTO `area_info` VALUES ('1188', '350505', '泉港区', '122', '13');
INSERT INTO `area_info` VALUES ('1975', '440605', '南海区', '205', '19');
INSERT INTO `area_info` VALUES ('1831', '430426', '祁东县', '189', '18');
INSERT INTO `area_info` VALUES ('1184', '350501', '市辖区', '122', '13');
INSERT INTO `area_info` VALUES ('2898', '620521', '清水县', '308', '28');
INSERT INTO `area_info` VALUES ('1030', '340201', '市辖区', '102', '12');
INSERT INTO `area_info` VALUES ('2612', '530824', '景谷傣族彝族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('2041', '441702', '江城区', '214', '19');
INSERT INTO `area_info` VALUES ('899', '321084', '高邮市', '86', '10');
INSERT INTO `area_info` VALUES ('1878', '430981', '沅江市', '194', '18');
INSERT INTO `area_info` VALUES ('475', '210203', '西岗区', '40', '6');
INSERT INTO `area_info` VALUES ('2775', '610203', '印台区', '295', '27');
INSERT INTO `area_info` VALUES ('2795', '610422', '三原县', '297', '27');
INSERT INTO `area_info` VALUES ('2830', '610631', '黄龙县', '299', '27');
INSERT INTO `area_info` VALUES ('1098', '341202', '颍州区', '111', '12');
INSERT INTO `area_info` VALUES ('741', '230826', '桦川县', '69', '8');
INSERT INTO `area_info` VALUES ('2627', '532301', '楚雄市', '279', '25');
INSERT INTO `area_info` VALUES ('170', '130803', '双滦区', '12', '3');
INSERT INTO `area_info` VALUES ('1258', '360323', '芦溪县', '129', '14');
INSERT INTO `area_info` VALUES ('2217', '469039', '中沙群岛的岛礁及其海域', '237', '21');
INSERT INTO `area_info` VALUES ('594', '220301', '市辖区', '55', '7');
INSERT INTO `area_info` VALUES ('2614', '530826', '江城哈尼族彝族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('2608', '530802', '翠云区', '277', '25');
INSERT INTO `area_info` VALUES ('2669', '532929', '云龙县', '283', '25');
INSERT INTO `area_info` VALUES ('1069', '340802', '迎江区', '108', '12');
INSERT INTO `area_info` VALUES ('801', '310230', '崇明县', '76', '9');
INSERT INTO `area_info` VALUES ('1751', '420982', '安陆市', '179', '17');
INSERT INTO `area_info` VALUES ('255', '140425', '平顺县', '19', '4');
INSERT INTO `area_info` VALUES ('2779', '610302', '渭滨区', '296', '27');
INSERT INTO `area_info` VALUES ('1500', '371724', '巨野县', '154', '15');
INSERT INTO `area_info` VALUES ('2397', '511826', '芦山县', '256', '23');
INSERT INTO `area_info` VALUES ('1754', '421002', '沙市区', '180', '17');
INSERT INTO `area_info` VALUES ('2230', '500113', '巴南区', '238', '22');
INSERT INTO `area_info` VALUES ('873', '320724', '灌南县', '83', '10');
INSERT INTO `area_info` VALUES ('1053', '340501', '市辖区', '105', '12');
INSERT INTO `area_info` VALUES ('2383', '511702', '通川区', '255', '23');
INSERT INTO `area_info` VALUES ('2899', '620522', '秦安县', '308', '28');
INSERT INTO `area_info` VALUES ('1702', '420301', '市辖区', '174', '17');
INSERT INTO `area_info` VALUES ('711', '230606', '大同区', '67', '8');
INSERT INTO `area_info` VALUES ('2369', '511523', '江安县', '253', '23');
INSERT INTO `area_info` VALUES ('3188', '460301', '市辖区', '382', '21');
INSERT INTO `area_info` VALUES ('1279', '360701', '市辖区', '133', '14');
INSERT INTO `area_info` VALUES ('1153', '350124', '闽清县', '118', '13');
INSERT INTO `area_info` VALUES ('1761', '421087', '松滋市', '180', '17');
INSERT INTO `area_info` VALUES ('2870', '611021', '洛南县', '303', '27');
INSERT INTO `area_info` VALUES ('297', '140827', '垣曲县', '23', '4');
INSERT INTO `area_info` VALUES ('1849', '430603', '云溪区', '191', '18');
INSERT INTO `area_info` VALUES ('715', '230624', '杜尔伯特蒙古族自治县', '67', '8');
INSERT INTO `area_info` VALUES ('2917', '620821', '泾川县', '311', '28');
INSERT INTO `area_info` VALUES ('2751', '542527', '措勤县', '292', '26');
INSERT INTO `area_info` VALUES ('541', '211005', '弓长岭区', '48', '6');
INSERT INTO `area_info` VALUES ('1732', '420682', '老河口市', '176', '17');
INSERT INTO `area_info` VALUES ('292', '140822', '万荣县', '23', '4');
INSERT INTO `area_info` VALUES ('3050', '650107', '达坂城区', '331', '31');
INSERT INTO `area_info` VALUES ('1350', '370103', '市中区', '138', '15');
INSERT INTO `area_info` VALUES ('2894', '620423', '景泰县', '307', '28');
INSERT INTO `area_info` VALUES ('1451', '371202', '莱城区', '149', '15');
INSERT INTO `area_info` VALUES ('1767', '421124', '英山县', '181', '17');
INSERT INTO `area_info` VALUES ('2950', '621221', '成　县', '315', '28');
INSERT INTO `area_info` VALUES ('1203', '350625', '长泰县', '123', '13');
INSERT INTO `area_info` VALUES ('2273', '510132', '新津县', '241', '23');
INSERT INTO `area_info` VALUES ('2978', '630105', '城北区', '318', '29');
INSERT INTO `area_info` VALUES ('2316', '510801', '市辖区', '247', '23');
INSERT INTO `area_info` VALUES ('1604', '411001', '市辖区', '164', '16');
INSERT INTO `area_info` VALUES ('1863', '430724', '临澧县', '192', '18');
INSERT INTO `area_info` VALUES ('1112', '341401', '市辖区', '113', '12');
INSERT INTO `area_info` VALUES ('383', '150426', '翁牛特旗', '30', '5');
INSERT INTO `area_info` VALUES ('2237', '500226', '荣昌县', '239', '22');
INSERT INTO `area_info` VALUES ('398', '150622', '准格尔旗', '32', '5');
INSERT INTO `area_info` VALUES ('2354', '511323', '蓬安县', '251', '23');
INSERT INTO `area_info` VALUES ('1164', '350213', '翔安区', '119', '13');
INSERT INTO `area_info` VALUES ('3136', '654322', '富蕴县', '344', '31');
INSERT INTO `area_info` VALUES ('259', '140429', '武乡县', '19', '4');
INSERT INTO `area_info` VALUES ('3193', '630000', '市辖区', '387', '25');
INSERT INTO `area_info` VALUES ('2624', '530925', '双江拉祜族佤族布朗族傣族自治县', '278', '25');
INSERT INTO `area_info` VALUES ('891', '320981', '东台市', '85', '10');
INSERT INTO `area_info` VALUES ('1973', '440601', '市辖区', '205', '19');
INSERT INTO `area_info` VALUES ('770', '231202', '北林区', '73', '8');
INSERT INTO `area_info` VALUES ('2375', '511529', '屏山县', '253', '23');
INSERT INTO `area_info` VALUES ('2145', '450923', '博白县', '229', '20');
INSERT INTO `area_info` VALUES ('391', '150523', '开鲁县', '31', '5');
INSERT INTO `area_info` VALUES ('2818', '610601', '市辖区', '299', '27');
INSERT INTO `area_info` VALUES ('2441', '513422', '木里藏族自治县', '261', '23');
INSERT INTO `area_info` VALUES ('2903', '620601', '市辖区', '309', '28');
INSERT INTO `area_info` VALUES ('2814', '610527', '白水县', '298', '27');
INSERT INTO `area_info` VALUES ('1078', '340828', '岳西县', '108', '12');
INSERT INTO `area_info` VALUES ('1050', '340405', '八公山区', '104', '12');
INSERT INTO `area_info` VALUES ('2102', '450323', '灵川县', '223', '20');
INSERT INTO `area_info` VALUES ('1268', '360428', '都昌县', '130', '14');
INSERT INTO `area_info` VALUES ('1869', '430811', '武陵源区', '193', '18');
INSERT INTO `area_info` VALUES ('1744', '420881', '钟祥市', '178', '17');
INSERT INTO `area_info` VALUES ('2922', '620826', '静宁县', '311', '28');
INSERT INTO `area_info` VALUES ('2701', '542128', '左贡县', '288', '26');
INSERT INTO `area_info` VALUES ('2199', '469001', '五指山市', '237', '21');
INSERT INTO `area_info` VALUES ('11', '110112', '通州区', '365', '1');
INSERT INTO `area_info` VALUES ('2474', '520303', '汇川区', '264', '24');
INSERT INTO `area_info` VALUES ('1283', '360723', '大余县', '133', '14');
INSERT INTO `area_info` VALUES ('1378', '370321', '桓台县', '140', '15');
INSERT INTO `area_info` VALUES ('2157', '451028', '乐业县', '230', '20');
INSERT INTO `area_info` VALUES ('1971', '440515', '澄海区', '204', '19');
INSERT INTO `area_info` VALUES ('1997', '440901', '市辖区', '208', '19');
INSERT INTO `area_info` VALUES ('3062', '652222', '巴里坤哈萨克自治县', '334', '31');
INSERT INTO `area_info` VALUES ('2821', '610622', '延川县', '299', '27');
INSERT INTO `area_info` VALUES ('1152', '350123', '罗源县', '118', '13');
INSERT INTO `area_info` VALUES ('1171', '350401', '市辖区', '121', '13');
INSERT INTO `area_info` VALUES ('2992', '632321', '同仁县', '321', '29');
INSERT INTO `area_info` VALUES ('2883', '620122', '皋兰县', '304', '28');
INSERT INTO `area_info` VALUES ('658', '230182', '双城市', '62', '8');
INSERT INTO `area_info` VALUES ('427', '150902', '集宁区', '35', '5');
INSERT INTO `area_info` VALUES ('113', '130527', '南和县', '9', '3');
INSERT INTO `area_info` VALUES ('2587', '530523', '龙陵县', '274', '25');
INSERT INTO `area_info` VALUES ('1156', '350181', '福清市', '118', '13');
INSERT INTO `area_info` VALUES ('2243', '500232', '武隆县', '239', '22');
INSERT INTO `area_info` VALUES ('2836', '610723', '洋　县', '300', '27');
INSERT INTO `area_info` VALUES ('2947', '621126', '岷　县', '314', '28');
INSERT INTO `area_info` VALUES ('2462', '520113', '白云区', '262', '24');
INSERT INTO `area_info` VALUES ('2026', '441427', '蕉岭县', '211', '19');
INSERT INTO `area_info` VALUES ('738', '230805', '东风区', '69', '8');
INSERT INTO `area_info` VALUES ('1111', '341324', '泗　县', '112', '12');
INSERT INTO `area_info` VALUES ('793', '310113', '宝山区', '367', '9');
INSERT INTO `area_info` VALUES ('1689', '420112', '东西湖区', '172', '17');
INSERT INTO `area_info` VALUES ('1313', '360902', '袁州区', '135', '14');
INSERT INTO `area_info` VALUES ('1187', '350504', '洛江区', '122', '13');
INSERT INTO `area_info` VALUES ('775', '231225', '明水县', '73', '8');
INSERT INTO `area_info` VALUES ('2718', '542322', '南木林县', '290', '26');
INSERT INTO `area_info` VALUES ('2968', '623022', '卓尼县', '317', '28');
INSERT INTO `area_info` VALUES ('2405', '512002', '雁江区', '258', '23');
INSERT INTO `area_info` VALUES ('980', '330701', '市辖区', '96', '11');
INSERT INTO `area_info` VALUES ('153', '130705', '宣化区', '11', '3');
INSERT INTO `area_info` VALUES ('1638', '411401', '市辖区', '168', '16');
INSERT INTO `area_info` VALUES ('1124', '341524', '金寨县', '114', '12');
INSERT INTO `area_info` VALUES ('804', '320103', '白下区', '77', '10');
INSERT INTO `area_info` VALUES ('1776', '421222', '通城县', '182', '17');
INSERT INTO `area_info` VALUES ('1305', '360825', '永丰县', '134', '14');
INSERT INTO `area_info` VALUES ('2251', '500241', '秀山土家族苗族自治县', '239', '22');
INSERT INTO `area_info` VALUES ('1408', '370702', '潍城区', '144', '15');
INSERT INTO `area_info` VALUES ('1986', '440785', '恩平市', '206', '19');
INSERT INTO `area_info` VALUES ('1944', '440203', '武江区', '201', '19');
INSERT INTO `area_info` VALUES ('2861', '610923', '宁陕县', '302', '27');
INSERT INTO `area_info` VALUES ('1342', '361126', '弋阳县', '137', '14');
INSERT INTO `area_info` VALUES ('687', '230382', '密山市', '64', '8');
INSERT INTO `area_info` VALUES ('2048', '441823', '阳山县', '215', '19');
INSERT INTO `area_info` VALUES ('2781', '610304', '陈仓区', '296', '27');
INSERT INTO `area_info` VALUES ('2093', '450226', '三江侗族自治县', '222', '20');
INSERT INTO `area_info` VALUES ('3120', '654022', '察布查尔锡伯自治县', '342', '31');
INSERT INTO `area_info` VALUES ('2811', '610524', '合阳县', '298', '27');
INSERT INTO `area_info` VALUES ('202', '131024', '香河县', '14', '3');
INSERT INTO `area_info` VALUES ('1433', '370901', '市辖区', '146', '15');
INSERT INTO `area_info` VALUES ('463', '210105', '皇姑区', '39', '6');
INSERT INTO `area_info` VALUES ('325', '141026', '安泽县', '25', '4');
INSERT INTO `area_info` VALUES ('677', '230281', '讷河市', '63', '8');
INSERT INTO `area_info` VALUES ('3181', '441901', '市辖区', '216', '19');
INSERT INTO `area_info` VALUES ('298', '140828', '夏　县', '23', '4');
INSERT INTO `area_info` VALUES ('261', '140431', '沁源县', '19', '4');
INSERT INTO `area_info` VALUES ('1856', '430682', '临湘市', '191', '18');
INSERT INTO `area_info` VALUES ('2012', '441284', '四会市', '209', '19');
INSERT INTO `area_info` VALUES ('1572', '410701', '市辖区', '161', '16');
INSERT INTO `area_info` VALUES ('1580', '410726', '延津县', '161', '16');
INSERT INTO `area_info` VALUES ('1144', '350101', '市辖区', '118', '13');
INSERT INTO `area_info` VALUES ('787', '310106', '静安区', '367', '9');
INSERT INTO `area_info` VALUES ('1344', '361128', '鄱阳县', '137', '14');
INSERT INTO `area_info` VALUES ('2525', '522626', '岑巩县', '269', '24');
INSERT INTO `area_info` VALUES ('2803', '610430', '淳化县', '297', '27');
INSERT INTO `area_info` VALUES ('3159', '666110', '四方区', '362', '58');
INSERT INTO `area_info` VALUES ('384', '150428', '喀喇沁旗', '30', '5');
INSERT INTO `area_info` VALUES ('180', '130901', '市辖区', '13', '3');
INSERT INTO `area_info` VALUES ('1177', '350425', '大田县', '121', '13');
INSERT INTO `area_info` VALUES ('2081', '450126', '宾阳县', '221', '20');
INSERT INTO `area_info` VALUES ('2688', '540122', '当雄县', '287', '26');
INSERT INTO `area_info` VALUES ('2561', '530129', '寻甸回族彝族自治县', '271', '25');
INSERT INTO `area_info` VALUES ('669', '230221', '龙江县', '63', '8');
INSERT INTO `area_info` VALUES ('2600', '530630', '水富县', '275', '25');
INSERT INTO `area_info` VALUES ('1812', '430224', '茶陵县', '187', '18');
INSERT INTO `area_info` VALUES ('654', '230127', '木兰县', '62', '8');
INSERT INTO `area_info` VALUES ('549', '211122', '盘山县', '49', '6');
INSERT INTO `area_info` VALUES ('334', '141081', '侯马市', '25', '4');
INSERT INTO `area_info` VALUES ('2380', '511623', '邻水县', '254', '23');
INSERT INTO `area_info` VALUES ('2227', '500110', '万盛区', '238', '22');
INSERT INTO `area_info` VALUES ('844', '320482', '金坛市', '80', '10');
INSERT INTO `area_info` VALUES ('2753', '542622', '工布江达县', '293', '26');
INSERT INTO `area_info` VALUES ('2007', '441223', '广宁县', '209', '19');
INSERT INTO `area_info` VALUES ('1436', '370921', '宁阳县', '146', '15');
INSERT INTO `area_info` VALUES ('788', '310107', '普陀区', '367', '9');
INSERT INTO `area_info` VALUES ('2700', '542127', '八宿县', '288', '26');
INSERT INTO `area_info` VALUES ('1564', '410527', '内黄县', '159', '16');
INSERT INTO `area_info` VALUES ('1674', '411723', '平舆县', '171', '16');
INSERT INTO `area_info` VALUES ('1655', '411525', '固始县', '169', '16');
INSERT INTO `area_info` VALUES ('1435', '370903', '岱岳区', '146', '15');
INSERT INTO `area_info` VALUES ('3001', '632621', '玛沁县', '323', '29');
INSERT INTO `area_info` VALUES ('2851', '610826', '绥德县', '301', '27');
INSERT INTO `area_info` VALUES ('580', '220122', '农安县', '53', '7');
INSERT INTO `area_info` VALUES ('2192', '451481', '凭祥市', '234', '20');
INSERT INTO `area_info` VALUES ('1109', '341322', '萧　县', '112', '12');
INSERT INTO `area_info` VALUES ('1219', '350784', '建阳市', '124', '13');
INSERT INTO `area_info` VALUES ('2254', '500381', '江津市', '240', '22');
INSERT INTO `area_info` VALUES ('2066', '445321', '新兴县', '220', '19');
INSERT INTO `area_info` VALUES ('509', '210603', '振兴区', '44', '6');
INSERT INTO `area_info` VALUES ('1740', '420802', '东宝区', '178', '17');
INSERT INTO `area_info` VALUES ('550', '211201', '市辖区', '50', '6');
INSERT INTO `area_info` VALUES ('2129', '450681', '东兴市', '226', '20');
INSERT INTO `area_info` VALUES ('3102', '653125', '莎车县', '340', '31');
INSERT INTO `area_info` VALUES ('2863', '610925', '岚皋县', '302', '27');
INSERT INTO `area_info` VALUES ('690', '230403', '工农区', '65', '8');
INSERT INTO `area_info` VALUES ('2054', '445101', '市辖区', '218', '19');
INSERT INTO `area_info` VALUES ('948', '330302', '鹿城区', '92', '11');
INSERT INTO `area_info` VALUES ('2638', '532502', '开远市', '280', '25');
INSERT INTO `area_info` VALUES ('864', '320683', '通州市', '82', '10');
INSERT INTO `area_info` VALUES ('3089', '652926', '拜城县', '338', '31');
INSERT INTO `area_info` VALUES ('2823', '610624', '安塞县', '299', '27');
INSERT INTO `area_info` VALUES ('1212', '350722', '浦城县', '124', '13');
INSERT INTO `area_info` VALUES ('3056', '650204', '白碱滩区', '332', '31');
INSERT INTO `area_info` VALUES ('1714', '420504', '点军区', '175', '17');
INSERT INTO `area_info` VALUES ('2218', '500101', '万州区', '238', '22');
INSERT INTO `area_info` VALUES ('1629', '411323', '西峡县', '167', '16');
INSERT INTO `area_info` VALUES ('914', '321284', '姜堰市', '88', '10');
INSERT INTO `area_info` VALUES ('1748', '420922', '大悟县', '179', '17');
INSERT INTO `area_info` VALUES ('2594', '530624', '大关县', '275', '25');
INSERT INTO `area_info` VALUES ('1608', '411025', '襄城县', '164', '16');
INSERT INTO `area_info` VALUES ('572', '211481', '兴城市', '52', '6');
INSERT INTO `area_info` VALUES ('141', '130634', '曲阳县', '10', '3');
INSERT INTO `area_info` VALUES ('1047', '340402', '大通区', '104', '12');
INSERT INTO `area_info` VALUES ('2233', '500222', '綦江县', '239', '22');
INSERT INTO `area_info` VALUES ('1438', '370982', '新泰市', '146', '15');
INSERT INTO `area_info` VALUES ('2692', '540126', '达孜县', '287', '26');
INSERT INTO `area_info` VALUES ('1759', '421081', '石首市', '180', '17');
INSERT INTO `area_info` VALUES ('2845', '610802', '榆阳区', '301', '27');
INSERT INTO `area_info` VALUES ('1841', '430524', '隆回县', '190', '18');
INSERT INTO `area_info` VALUES ('611', '220524', '柳河县', '57', '7');
INSERT INTO `area_info` VALUES ('2557', '530125', '宜良县', '271', '25');
INSERT INTO `area_info` VALUES ('2046', '441802', '清城区', '215', '19');
INSERT INTO `area_info` VALUES ('838', '320402', '天宁区', '80', '10');
INSERT INTO `area_info` VALUES ('557', '211282', '开原市', '50', '6');
INSERT INTO `area_info` VALUES ('1105', '341282', '界首市', '111', '12');
INSERT INTO `area_info` VALUES ('449', '152525', '东乌珠穆沁旗', '37', '5');
INSERT INTO `area_info` VALUES ('452', '152528', '镶黄旗', '37', '5');
INSERT INTO `area_info` VALUES ('2935', '621023', '华池县', '313', '28');
INSERT INTO `area_info` VALUES ('2981', '630123', '湟源县', '318', '29');
INSERT INTO `area_info` VALUES ('2475', '520321', '遵义县', '264', '24');
INSERT INTO `area_info` VALUES ('1005', '331004', '路桥区', '99', '11');
INSERT INTO `area_info` VALUES ('137', '130630', '涞源县', '10', '3');
INSERT INTO `area_info` VALUES ('1540', '410326', '汝阳县', '157', '16');
INSERT INTO `area_info` VALUES ('2672', '532932', '鹤庆县', '283', '25');
INSERT INTO `area_info` VALUES ('1565', '410581', '林州市', '159', '16');
INSERT INTO `area_info` VALUES ('2632', '532326', '大姚县', '279', '25');
INSERT INTO `area_info` VALUES ('1654', '411524', '商城县', '169', '16');
INSERT INTO `area_info` VALUES ('852', '320581', '常熟市', '81', '10');
INSERT INTO `area_info` VALUES ('1675', '411724', '正阳县', '171', '16');
INSERT INTO `area_info` VALUES ('1955', '440303', '罗湖区', '202', '19');
INSERT INTO `area_info` VALUES ('1412', '370724', '临朐县', '144', '15');
INSERT INTO `area_info` VALUES ('28', '120110', '东丽区', '366', '2');
INSERT INTO `area_info` VALUES ('1293', '360733', '会昌县', '133', '14');
INSERT INTO `area_info` VALUES ('2717', '542301', '日喀则市', '290', '26');
INSERT INTO `area_info` VALUES ('1122', '341522', '霍邱县', '114', '12');
INSERT INTO `area_info` VALUES ('963', '330424', '海盐县', '93', '11');
INSERT INTO `area_info` VALUES ('1730', '420625', '谷城县', '176', '17');
INSERT INTO `area_info` VALUES ('1883', '431022', '宜章县', '195', '18');
INSERT INTO `area_info` VALUES ('3003', '632623', '甘德县', '323', '29');
INSERT INTO `area_info` VALUES ('1002', '331001', '市辖区', '99', '11');
INSERT INTO `area_info` VALUES ('1281', '360721', '赣　县', '133', '14');
INSERT INTO `area_info` VALUES ('2519', '522428', '赫章县', '268', '24');
INSERT INTO `area_info` VALUES ('289', '140801', '市辖区', '23', '4');
INSERT INTO `area_info` VALUES ('1075', '340825', '太湖县', '108', '12');
INSERT INTO `area_info` VALUES ('2259', '510104', '锦江区', '241', '23');
INSERT INTO `area_info` VALUES ('118', '130532', '平乡县', '9', '3');
INSERT INTO `area_info` VALUES ('539', '211003', '文圣区', '48', '6');
INSERT INTO `area_info` VALUES ('3195', '632000', '市辖区', '389', '31');
INSERT INTO `area_info` VALUES ('243', '140301', '市辖区', '18', '4');
INSERT INTO `area_info` VALUES ('785', '310104', '徐汇区', '367', '9');
INSERT INTO `area_info` VALUES ('2167', '451202', '金城江区', '232', '20');
INSERT INTO `area_info` VALUES ('369', '150223', '达尔罕茂明安联合旗', '28', '5');
INSERT INTO `area_info` VALUES ('2070', '450101', '市辖区', '221', '20');
INSERT INTO `area_info` VALUES ('1148', '350105', '马尾区', '118', '13');
INSERT INTO `area_info` VALUES ('2501', '522228', '沿河土家族自治县', '266', '24');
INSERT INTO `area_info` VALUES ('2840', '610727', '略阳县', '300', '27');
INSERT INTO `area_info` VALUES ('3076', '652822', '轮台县', '337', '31');
INSERT INTO `area_info` VALUES ('1197', '350601', '市辖区', '123', '13');
INSERT INTO `area_info` VALUES ('2291', '510501', '市辖区', '244', '23');
INSERT INTO `area_info` VALUES ('647', '230108', '平房区', '62', '8');
INSERT INTO `area_info` VALUES ('867', '320703', '连云区', '83', '10');
INSERT INTO `area_info` VALUES ('304', '140902', '忻府区', '24', '4');
INSERT INTO `area_info` VALUES ('656', '230129', '延寿县', '62', '8');
INSERT INTO `area_info` VALUES ('266', '140522', '阳城县', '20', '4');
INSERT INTO `area_info` VALUES ('649', '230111', '呼兰区', '62', '8');
INSERT INTO `area_info` VALUES ('701', '230506', '宝山区', '66', '8');
INSERT INTO `area_info` VALUES ('906', '321182', '扬中市', '87', '10');
INSERT INTO `area_info` VALUES ('3155', '444120', '锦湖区', '358', '54');
INSERT INTO `area_info` VALUES ('1019', '331126', '庆元县', '100', '11');
INSERT INTO `area_info` VALUES ('1474', '371427', '夏津县', '151', '15');
INSERT INTO `area_info` VALUES ('71', '130227', '迁西县', '6', '3');
INSERT INTO `area_info` VALUES ('2682', '533421', '香格里拉县', '286', '25');
INSERT INTO `area_info` VALUES ('1217', '350782', '武夷山市', '124', '13');
INSERT INTO `area_info` VALUES ('2980', '630122', '湟中县', '318', '29');
INSERT INTO `area_info` VALUES ('477', '210211', '甘井子区', '40', '6');
INSERT INTO `area_info` VALUES ('2642', '532525', '石屏县', '280', '25');
INSERT INTO `area_info` VALUES ('881', '320830', '盱眙县', '84', '10');
INSERT INTO `area_info` VALUES ('635', '222403', '敦化市', '61', '7');
INSERT INTO `area_info` VALUES ('2586', '530522', '腾冲县', '274', '25');
INSERT INTO `area_info` VALUES ('425', '150826', '杭锦后旗', '34', '5');
INSERT INTO `area_info` VALUES ('2659', '532822', '勐海县', '282', '25');
INSERT INTO `area_info` VALUES ('1379', '370322', '高青县', '140', '15');
INSERT INTO `area_info` VALUES ('1818', '430321', '湘潭县', '188', '18');
INSERT INTO `area_info` VALUES ('336', '141101', '市辖区', '26', '4');
INSERT INTO `area_info` VALUES ('1440', '371001', '市辖区', '147', '15');
INSERT INTO `area_info` VALUES ('2524', '522625', '镇远县', '269', '24');
INSERT INTO `area_info` VALUES ('1667', '411627', '太康县', '170', '16');
INSERT INTO `area_info` VALUES ('2783', '610323', '岐山县', '296', '27');
INSERT INTO `area_info` VALUES ('742', '230828', '汤原县', '69', '8');
INSERT INTO `area_info` VALUES ('1563', '410526', '滑　县', '159', '16');
INSERT INTO `area_info` VALUES ('2005', '441202', '端州区', '209', '19');
INSERT INTO `area_info` VALUES ('1262', '360421', '九江县', '130', '14');
INSERT INTO `area_info` VALUES ('2279', '510302', '自流井区', '242', '23');
INSERT INTO `area_info` VALUES ('2668', '532928', '永平县', '283', '25');
INSERT INTO `area_info` VALUES ('35', '120223', '静海县', '366', '2');
INSERT INTO `area_info` VALUES ('282', '140724', '昔阳县', '368', '4');
INSERT INTO `area_info` VALUES ('1466', '371401', '市辖区', '151', '15');
INSERT INTO `area_info` VALUES ('1530', '410302', '老城区', '157', '16');
INSERT INTO `area_info` VALUES ('621', '220701', '市辖区', '59', '7');
INSERT INTO `area_info` VALUES ('2613', '530825', '镇沅彝族哈尼族拉祜族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('684', '230307', '麻山区', '64', '8');
INSERT INTO `area_info` VALUES ('437', '150981', '丰镇市', '35', '5');
INSERT INTO `area_info` VALUES ('1822', '430405', '珠晖区', '189', '18');
INSERT INTO `area_info` VALUES ('376', '150403', '元宝山区', '30', '5');
INSERT INTO `area_info` VALUES ('2229', '500112', '渝北区', '238', '22');
INSERT INTO `area_info` VALUES ('1319', '360926', '铜鼓县', '135', '14');
INSERT INTO `area_info` VALUES ('1265', '360425', '永修县', '130', '14');
INSERT INTO `area_info` VALUES ('2056', '445121', '潮安县', '218', '19');
INSERT INTO `area_info` VALUES ('218', '131181', '冀州市', '15', '3');
INSERT INTO `area_info` VALUES ('1613', '411103', '郾城区', '165', '16');
INSERT INTO `area_info` VALUES ('856', '320585', '太仓市', '81', '10');
INSERT INTO `area_info` VALUES ('924', '330104', '江干区', '90', '11');
INSERT INTO `area_info` VALUES ('1699', '420205', '铁山区', '173', '17');
INSERT INTO `area_info` VALUES ('1764', '421121', '团风县', '181', '17');
INSERT INTO `area_info` VALUES ('2875', '611026', '柞水县', '303', '27');
INSERT INTO `area_info` VALUES ('1575', '410704', '凤泉区', '161', '16');
INSERT INTO `area_info` VALUES ('92', '130425', '大名县', '8', '3');
INSERT INTO `area_info` VALUES ('1624', '411301', '市辖区', '167', '16');
INSERT INTO `area_info` VALUES ('50', '130128', '深泽县', '5', '3');
INSERT INTO `area_info` VALUES ('481', '210281', '瓦房店市', '40', '6');
INSERT INTO `area_info` VALUES ('547', '211103', '兴隆台区', '49', '6');
INSERT INTO `area_info` VALUES ('3132', '654225', '裕民县', '343', '31');
INSERT INTO `area_info` VALUES ('413', '150781', '满洲里市', '33', '5');
INSERT INTO `area_info` VALUES ('1976', '440606', '顺德区', '205', '19');
INSERT INTO `area_info` VALUES ('819', '320204', '北塘区', '78', '10');
INSERT INTO `area_info` VALUES ('2735', '542421', '那曲县', '291', '26');
INSERT INTO `area_info` VALUES ('2149', '451002', '右江区', '230', '20');
INSERT INTO `area_info` VALUES ('411', '150726', '新巴尔虎左旗', '33', '5');
INSERT INTO `area_info` VALUES ('1912', '431228', '芷江侗族自治县', '197', '18');
INSERT INTO `area_info` VALUES ('3116', '653227', '民丰县', '341', '31');
INSERT INTO `area_info` VALUES ('2900', '620523', '甘谷县', '308', '28');
INSERT INTO `area_info` VALUES ('2297', '510524', '叙永县', '244', '23');
INSERT INTO `area_info` VALUES ('3183', '531001', '市辖区', '377', '25');
INSERT INTO `area_info` VALUES ('3052', '650121', '乌鲁木齐县', '331', '31');
INSERT INTO `area_info` VALUES ('1538', '410324', '栾川县', '157', '16');
INSERT INTO `area_info` VALUES ('3163', '', '南区', '372', '33');
INSERT INTO `area_info` VALUES ('2698', '542125', '丁青县', '288', '26');
INSERT INTO `area_info` VALUES ('2029', '441502', '城　区', '212', '19');
INSERT INTO `area_info` VALUES ('2414', '513226', '金川县', '259', '23');
INSERT INTO `area_info` VALUES ('1073', '340823', '枞阳县', '108', '12');
INSERT INTO `area_info` VALUES ('1915', '431281', '洪江市', '197', '18');
INSERT INTO `area_info` VALUES ('291', '140821', '临猗县', '23', '4');
INSERT INTO `area_info` VALUES ('1318', '360925', '靖安县', '135', '14');
INSERT INTO `area_info` VALUES ('1788', '422826', '咸丰县', '184', '17');
INSERT INTO `area_info` VALUES ('2078', '450123', '隆安县', '221', '20');
INSERT INTO `area_info` VALUES ('112', '130526', '任　县', '9', '3');
INSERT INTO `area_info` VALUES ('1945', '440204', '浈江区', '201', '19');
INSERT INTO `area_info` VALUES ('1560', '410506', '龙安区', '159', '16');
INSERT INTO `area_info` VALUES ('1117', '341424', '和　县', '113', '12');
INSERT INTO `area_info` VALUES ('1301', '360821', '吉安县', '134', '14');
INSERT INTO `area_info` VALUES ('1820', '430382', '韶山市', '188', '18');
INSERT INTO `area_info` VALUES ('30', '120112', '津南区', '366', '2');
INSERT INTO `area_info` VALUES ('2503', '522230', '万山特区', '266', '24');
INSERT INTO `area_info` VALUES ('305', '140921', '定襄县', '24', '4');
INSERT INTO `area_info` VALUES ('678', '230301', '市辖区', '64', '8');
INSERT INTO `area_info` VALUES ('2813', '610526', '蒲城县', '298', '27');
INSERT INTO `area_info` VALUES ('655', '230128', '通河县', '62', '8');
INSERT INTO `area_info` VALUES ('942', '330225', '象山县', '91', '11');
INSERT INTO `area_info` VALUES ('2854', '610829', '吴堡县', '301', '27');
INSERT INTO `area_info` VALUES ('65', '130205', '开平区', '6', '3');
INSERT INTO `area_info` VALUES ('194', '130982', '任丘市', '13', '3');
INSERT INTO `area_info` VALUES ('874', '320801', '市辖区', '84', '10');
INSERT INTO `area_info` VALUES ('1937', '440111', '白云区', '200', '19');
INSERT INTO `area_info` VALUES ('1774', '421202', '咸安区', '182', '17');
INSERT INTO `area_info` VALUES ('971', '330522', '长兴县', '94', '11');
INSERT INTO `area_info` VALUES ('235', '140212', '新荣区', '17', '4');
INSERT INTO `area_info` VALUES ('3060', '652123', '托克逊县', '333', '31');
INSERT INTO `area_info` VALUES ('364', '150205', '石拐区', '28', '5');
INSERT INTO `area_info` VALUES ('1490', '371622', '阳信县', '153', '15');
INSERT INTO `area_info` VALUES ('2137', '450803', '港南区', '228', '20');
INSERT INTO `area_info` VALUES ('2096', '450303', '叠彩区', '223', '20');
INSERT INTO `area_info` VALUES ('763', '231102', '爱辉区', '72', '8');
INSERT INTO `area_info` VALUES ('75', '130283', '迁安市', '6', '3');
INSERT INTO `area_info` VALUES ('146', '130681', '涿州市', '10', '3');
INSERT INTO `area_info` VALUES ('2534', '522635', '麻江县', '269', '24');
INSERT INTO `area_info` VALUES ('2808', '610521', '华　县', '298', '27');
INSERT INTO `area_info` VALUES ('1096', '341182', '明光市', '110', '12');
INSERT INTO `area_info` VALUES ('584', '220201', '市辖区', '54', '7');
INSERT INTO `area_info` VALUES ('49', '130127', '高邑县', '5', '3');
INSERT INTO `area_info` VALUES ('414', '150782', '牙克石市', '33', '5');
INSERT INTO `area_info` VALUES ('2747', '542523', '噶尔县', '292', '26');
INSERT INTO `area_info` VALUES ('1641', '411421', '民权县', '168', '16');
INSERT INTO `area_info` VALUES ('188', '130926', '肃宁县', '13', '3');
INSERT INTO `area_info` VALUES ('2943', '621122', '陇西县', '314', '28');
INSERT INTO `area_info` VALUES ('290', '140802', '盐湖区', '23', '4');
INSERT INTO `area_info` VALUES ('2067', '445322', '郁南县', '220', '19');
INSERT INTO `area_info` VALUES ('2984', '632123', '乐都县', '319', '29');
INSERT INTO `area_info` VALUES ('2560', '530128', '禄劝彝族苗族自治县', '271', '25');
INSERT INTO `area_info` VALUES ('1223', '350822', '永定县', '125', '13');
INSERT INTO `area_info` VALUES ('1543', '410329', '伊川县', '157', '16');
INSERT INTO `area_info` VALUES ('631', '220881', '洮南市', '60', '7');
INSERT INTO `area_info` VALUES ('1192', '350526', '德化县', '122', '13');
INSERT INTO `area_info` VALUES ('608', '220503', '二道江区', '57', '7');
INSERT INTO `area_info` VALUES ('1273', '360502', '渝水区', '131', '14');
INSERT INTO `area_info` VALUES ('3109', '653201', '和田市', '341', '31');
INSERT INTO `area_info` VALUES ('2609', '530821', '普洱哈尼族彝族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('975', '330621', '绍兴县', '95', '11');
INSERT INTO `area_info` VALUES ('296', '140826', '绛　县', '23', '4');
INSERT INTO `area_info` VALUES ('883', '320901', '市辖区', '85', '10');
INSERT INTO `area_info` VALUES ('827', '320303', '云龙区', '79', '10');
INSERT INTO `area_info` VALUES ('1535', '410307', '洛龙区', '157', '16');
INSERT INTO `area_info` VALUES ('593', '220284', '磐石市', '54', '7');
INSERT INTO `area_info` VALUES ('2458', '520102', '南明区', '262', '24');
INSERT INTO `area_info` VALUES ('2591', '530621', '鲁甸县', '275', '25');
INSERT INTO `area_info` VALUES ('829', '320305', '贾汪区', '79', '10');
INSERT INTO `area_info` VALUES ('2773', '610201', '市辖区', '295', '27');
INSERT INTO `area_info` VALUES ('2080', '450125', '上林县', '221', '20');
INSERT INTO `area_info` VALUES ('2057', '445122', '饶平县', '218', '19');
INSERT INTO `area_info` VALUES ('216', '131127', '景　县', '15', '3');
INSERT INTO `area_info` VALUES ('2506', '522323', '普安县', '267', '24');
INSERT INTO `area_info` VALUES ('2262', '510107', '武侯区', '241', '23');
INSERT INTO `area_info` VALUES ('288', '140781', '介休市', '368', '4');
INSERT INTO `area_info` VALUES ('2003', '440983', '信宜市', '208', '19');
INSERT INTO `area_info` VALUES ('2670', '532930', '洱源县', '283', '25');
INSERT INTO `area_info` VALUES ('3129', '654221', '额敏县', '343', '31');
INSERT INTO `area_info` VALUES ('2703', '542132', '洛隆县', '288', '26');
INSERT INTO `area_info` VALUES ('357', '150123', '和林格尔县', '27', '5');
INSERT INTO `area_info` VALUES ('213', '131124', '饶阳县', '15', '3');
INSERT INTO `area_info` VALUES ('2925', '620921', '金塔县', '312', '28');
INSERT INTO `area_info` VALUES ('3037', '640423', '隆德县', '329', '30');
INSERT INTO `area_info` VALUES ('158', '130724', '沽源县', '11', '3');
INSERT INTO `area_info` VALUES ('2918', '620822', '灵台县', '311', '28');
INSERT INTO `area_info` VALUES ('352', '150103', '回民区', '27', '5');
INSERT INTO `area_info` VALUES ('47', '130125', '行唐县', '5', '3');
INSERT INTO `area_info` VALUES ('417', '150785', '根河市', '33', '5');
INSERT INTO `area_info` VALUES ('1561', '410522', '安阳县', '159', '16');
INSERT INTO `area_info` VALUES ('2341', '511123', '犍为县', '250', '23');
INSERT INTO `area_info` VALUES ('1348', '370101', '市辖区', '138', '15');
INSERT INTO `area_info` VALUES ('512', '210681', '东港市', '44', '6');
INSERT INTO `area_info` VALUES ('2402', '511922', '南江县', '257', '23');
INSERT INTO `area_info` VALUES ('2353', '511322', '营山县', '251', '23');
INSERT INTO `area_info` VALUES ('2924', '620902', '肃州区', '312', '28');
INSERT INTO `area_info` VALUES ('2322', '510823', '剑阁县', '247', '23');
INSERT INTO `area_info` VALUES ('278', '140702', '榆次区', '368', '4');
INSERT INTO `area_info` VALUES ('915', '321301', '市辖区', '89', '10');
INSERT INTO `area_info` VALUES ('2010', '441226', '德庆县', '209', '19');
INSERT INTO `area_info` VALUES ('573', '220101', '市辖区', '53', '7');
INSERT INTO `area_info` VALUES ('1961', '440401', '市辖区', '203', '19');
INSERT INTO `area_info` VALUES ('2206', '469026', '屯昌县', '237', '21');
INSERT INTO `area_info` VALUES ('144', '130637', '博野县', '10', '3');
INSERT INTO `area_info` VALUES ('1401', '370682', '莱阳市', '143', '15');
INSERT INTO `area_info` VALUES ('2077', '450122', '武鸣县', '221', '20');
INSERT INTO `area_info` VALUES ('1584', '410782', '辉县市', '161', '16');
INSERT INTO `area_info` VALUES ('31', '120113', '北辰区', '366', '2');
INSERT INTO `area_info` VALUES ('237', '140222', '天镇县', '17', '4');
INSERT INTO `area_info` VALUES ('2305', '510683', '绵竹市', '245', '23');
INSERT INTO `area_info` VALUES ('339', '141122', '交城县', '26', '4');
INSERT INTO `area_info` VALUES ('1525', '410222', '通许县', '156', '16');
INSERT INTO `area_info` VALUES ('2437', '513336', '乡城县', '260', '23');
INSERT INTO `area_info` VALUES ('2792', '610402', '秦都区', '297', '27');
INSERT INTO `area_info` VALUES ('653', '230126', '巴彦县', '62', '8');
INSERT INTO `area_info` VALUES ('718', '230703', '南岔区', '68', '8');
INSERT INTO `area_info` VALUES ('186', '130924', '海兴县', '13', '3');
INSERT INTO `area_info` VALUES ('372', '150303', '海南区', '29', '5');
INSERT INTO `area_info` VALUES ('2094', '450301', '市辖区', '223', '20');
INSERT INTO `area_info` VALUES ('236', '140221', '阳高县', '17', '4');
INSERT INTO `area_info` VALUES ('1983', '440781', '台山市', '206', '19');
INSERT INTO `area_info` VALUES ('2107', '450328', '龙胜各族自治县', '223', '20');
INSERT INTO `area_info` VALUES ('923', '330103', '下城区', '90', '11');
INSERT INTO `area_info` VALUES ('321', '141022', '翼城县', '25', '4');
INSERT INTO `area_info` VALUES ('2771', '610125', '户　县', '294', '27');
INSERT INTO `area_info` VALUES ('2805', '610481', '兴平市', '297', '27');
INSERT INTO `area_info` VALUES ('2651', '532622', '砚山县', '281', '25');
INSERT INTO `area_info` VALUES ('2300', '510603', '旌阳区', '245', '23');
INSERT INTO `area_info` VALUES ('2634', '532328', '元谋县', '279', '25');
INSERT INTO `area_info` VALUES ('1664', '411624', '沈丘县', '170', '16');
INSERT INTO `area_info` VALUES ('1645', '411425', '虞城县', '168', '16');
INSERT INTO `area_info` VALUES ('2635', '532329', '武定县', '279', '25');
INSERT INTO `area_info` VALUES ('1676', '411725', '确山县', '171', '16');
INSERT INTO `area_info` VALUES ('1143', '341881', '宁国市', '117', '12');
INSERT INTO `area_info` VALUES ('759', '231083', '海林市', '71', '8');
INSERT INTO `area_info` VALUES ('199', '131003', '广阳区', '14', '3');
INSERT INTO `area_info` VALUES ('3184', '522301', '市辖区', '378', '24');
INSERT INTO `area_info` VALUES ('375', '150402', '红山区', '30', '5');
INSERT INTO `area_info` VALUES ('2053', '441882', '连州市', '215', '19');
INSERT INTO `area_info` VALUES ('1618', '411202', '湖滨区', '166', '16');
INSERT INTO `area_info` VALUES ('2766', '610114', '阎良区', '294', '27');
INSERT INTO `area_info` VALUES ('1487', '371601', '市辖区', '153', '15');
INSERT INTO `area_info` VALUES ('709', '230604', '让胡路区', '67', '8');
INSERT INTO `area_info` VALUES ('537', '211001', '市辖区', '48', '6');
INSERT INTO `area_info` VALUES ('2826', '610627', '甘泉县', '299', '27');
INSERT INTO `area_info` VALUES ('1946', '440205', '曲江区', '201', '19');
INSERT INTO `area_info` VALUES ('1698', '420204', '下陆区', '173', '17');
INSERT INTO `area_info` VALUES ('1046', '340401', '市辖区', '104', '12');
INSERT INTO `area_info` VALUES ('2122', '450503', '银海区', '225', '20');
INSERT INTO `area_info` VALUES ('1896', '431123', '双牌县', '196', '18');
INSERT INTO `area_info` VALUES ('1327', '361023', '南丰县', '136', '14');
INSERT INTO `area_info` VALUES ('2264', '510112', '龙泉驿区', '241', '23');
INSERT INTO `area_info` VALUES ('968', '330502', '吴兴区', '94', '11');
INSERT INTO `area_info` VALUES ('2812', '610525', '澄城县', '298', '27');
INSERT INTO `area_info` VALUES ('768', '231182', '五大连池市', '72', '8');
INSERT INTO `area_info` VALUES ('2252', '500242', '酉阳土家族苗族自治县', '239', '22');
INSERT INTO `area_info` VALUES ('2919', '620823', '崇信县', '311', '28');
INSERT INTO `area_info` VALUES ('1437', '370923', '东平县', '146', '15');
INSERT INTO `area_info` VALUES ('2177', '451281', '宜州市', '232', '20');
INSERT INTO `area_info` VALUES ('1907', '431223', '辰溪县', '197', '18');
INSERT INTO `area_info` VALUES ('2086', '450204', '柳南区', '222', '20');
INSERT INTO `area_info` VALUES ('1719', '420527', '秭归县', '175', '17');
INSERT INTO `area_info` VALUES ('3066', '652303', '米泉市', '335', '31');
INSERT INTO `area_info` VALUES ('272', '140603', '平鲁区', '21', '4');
INSERT INTO `area_info` VALUES ('1593', '410825', '温　县', '162', '16');
INSERT INTO `area_info` VALUES ('447', '152523', '苏尼特左旗', '37', '5');
INSERT INTO `area_info` VALUES ('2655', '532626', '丘北县', '281', '25');
INSERT INTO `area_info` VALUES ('1848', '430602', '岳阳楼区', '191', '18');
INSERT INTO `area_info` VALUES ('2699', '542126', '察雅县', '288', '26');
INSERT INTO `area_info` VALUES ('555', '211224', '昌图县', '50', '6');
INSERT INTO `area_info` VALUES ('1054', '340502', '金家庄区', '105', '12');
INSERT INTO `area_info` VALUES ('507', '210601', '市辖区', '44', '6');
INSERT INTO `area_info` VALUES ('1694', '420117', '新洲区', '172', '17');
INSERT INTO `area_info` VALUES ('2551', '530111', '官渡区', '271', '25');
INSERT INTO `area_info` VALUES ('254', '140424', '屯留县', '19', '4');
INSERT INTO `area_info` VALUES ('2949', '621202', '武都区', '315', '28');
INSERT INTO `area_info` VALUES ('1633', '411327', '社旗县', '167', '16');
INSERT INTO `area_info` VALUES ('2822', '610623', '子长县', '299', '27');
INSERT INTO `area_info` VALUES ('1481', '371522', '莘　县', '152', '15');
INSERT INTO `area_info` VALUES ('988', '330783', '东阳市', '96', '11');
INSERT INTO `area_info` VALUES ('1174', '350421', '明溪县', '121', '13');
INSERT INTO `area_info` VALUES ('1566', '410601', '市辖区', '160', '16');
INSERT INTO `area_info` VALUES ('1648', '411501', '市辖区', '169', '16');
INSERT INTO `area_info` VALUES ('2250', '500240', '石柱土家族自治县', '239', '22');
INSERT INTO `area_info` VALUES ('446', '152522', '阿巴嘎旗', '37', '5');
INSERT INTO `area_info` VALUES ('933', '330183', '富阳市', '90', '11');
INSERT INTO `area_info` VALUES ('1914', '431230', '通道侗族自治县', '197', '18');
INSERT INTO `area_info` VALUES ('2118', '450423', '蒙山县', '224', '20');
INSERT INTO `area_info` VALUES ('3006', '632626', '玛多县', '323', '29');
INSERT INTO `area_info` VALUES ('1576', '410711', '牧野区', '161', '16');
INSERT INTO `area_info` VALUES ('1137', '341802', '宣州区', '117', '12');
INSERT INTO `area_info` VALUES ('2319', '510812', '朝天区', '247', '23');
INSERT INTO `area_info` VALUES ('2109', '450330', '平乐县', '223', '20');
INSERT INTO `area_info` VALUES ('1238', '360101', '市辖区', '127', '14');
INSERT INTO `area_info` VALUES ('972', '330523', '安吉县', '94', '11');
INSERT INTO `area_info` VALUES ('1591', '410822', '博爱县', '162', '16');
INSERT INTO `area_info` VALUES ('319', '141002', '尧都区', '25', '4');
INSERT INTO `area_info` VALUES ('1845', '430529', '城步苗族自治县', '190', '18');
INSERT INTO `area_info` VALUES ('22', '120104', '南开区', '366', '2');
INSERT INTO `area_info` VALUES ('355', '150121', '土默特左旗', '27', '5');
INSERT INTO `area_info` VALUES ('1606', '411023', '许昌县', '164', '16');
INSERT INTO `area_info` VALUES ('747', '230902', '新兴区', '70', '8');
INSERT INTO `area_info` VALUES ('2732', '542336', '聂拉木县', '290', '26');
INSERT INTO `area_info` VALUES ('739', '230811', '郊　区', '69', '8');
INSERT INTO `area_info` VALUES ('582', '220182', '榆树市', '53', '7');
INSERT INTO `area_info` VALUES ('1889', '431028', '安仁县', '195', '18');
INSERT INTO `area_info` VALUES ('2746', '542522', '札达县', '292', '26');
INSERT INTO `area_info` VALUES ('2194', '460105', '秀英区', '235', '21');
INSERT INTO `area_info` VALUES ('2539', '522723', '贵定县', '270', '24');
INSERT INTO `area_info` VALUES ('1158', '350201', '市辖区', '119', '13');
INSERT INTO `area_info` VALUES ('903', '321111', '润州区', '87', '10');
INSERT INTO `area_info` VALUES ('1146', '350103', '台江区', '118', '13');
INSERT INTO `area_info` VALUES ('203', '131025', '大城县', '14', '3');
INSERT INTO `area_info` VALUES ('390', '150522', '科尔沁左翼后旗', '31', '5');
INSERT INTO `area_info` VALUES ('464', '210106', '铁西区', '39', '6');
INSERT INTO `area_info` VALUES ('2449', '513430', '金阳县', '261', '23');
INSERT INTO `area_info` VALUES ('287', '140729', '灵石县', '368', '4');
INSERT INTO `area_info` VALUES ('853', '320582', '张家港市', '81', '10');
INSERT INTO `area_info` VALUES ('2977', '630104', '城西区', '318', '29');
INSERT INTO `area_info` VALUES ('1901', '431128', '新田县', '196', '18');
INSERT INTO `area_info` VALUES ('794', '310114', '嘉定区', '367', '9');
INSERT INTO `area_info` VALUES ('2839', '610726', '宁强县', '300', '27');
INSERT INTO `area_info` VALUES ('2626', '530927', '沧源佤族自治县', '278', '25');
INSERT INTO `area_info` VALUES ('1603', '410928', '濮阳县', '163', '16');
INSERT INTO `area_info` VALUES ('1071', '340811', '郊　区', '108', '12');
INSERT INTO `area_info` VALUES ('1813', '430225', '炎陵县', '187', '18');
INSERT INTO `area_info` VALUES ('1432', '370883', '邹城市', '145', '15');
INSERT INTO `area_info` VALUES ('2776', '610204', '耀州区', '295', '27');
INSERT INTO `area_info` VALUES ('1059', '340602', '杜集区', '106', '12');
INSERT INTO `area_info` VALUES ('2476', '520322', '桐梓县', '264', '24');
INSERT INTO `area_info` VALUES ('2133', '450721', '灵山县', '227', '20');
INSERT INTO `area_info` VALUES ('601', '220401', '市辖区', '56', '7');
INSERT INTO `area_info` VALUES ('2892', '620421', '靖远县', '307', '28');
INSERT INTO `area_info` VALUES ('596', '220303', '铁东区', '55', '7');
INSERT INTO `area_info` VALUES ('1288', '360728', '定南县', '133', '14');
INSERT INTO `area_info` VALUES ('1725', '420601', '市辖区', '176', '17');
INSERT INTO `area_info` VALUES ('1815', '430301', '市辖区', '188', '18');
INSERT INTO `area_info` VALUES ('1854', '430626', '平江县', '191', '18');
INSERT INTO `area_info` VALUES ('1118', '341501', '市辖区', '114', '12');
INSERT INTO `area_info` VALUES ('2089', '450222', '柳城县', '222', '20');
INSERT INTO `area_info` VALUES ('2849', '610824', '靖边县', '301', '27');
INSERT INTO `area_info` VALUES ('2553', '530113', '东川区', '271', '25');
INSERT INTO `area_info` VALUES ('2963', '622925', '和政县', '316', '28');
INSERT INTO `area_info` VALUES ('807', '320106', '鼓楼区', '77', '10');
INSERT INTO `area_info` VALUES ('2665', '532925', '弥渡县', '283', '25');
INSERT INTO `area_info` VALUES ('2306', '510701', '市辖区', '246', '23');
INSERT INTO `area_info` VALUES ('3075', '652801', '库尔勒市', '337', '31');
INSERT INTO `area_info` VALUES ('185', '130923', '东光县', '13', '3');
INSERT INTO `area_info` VALUES ('1785', '422822', '建始县', '184', '17');
INSERT INTO `area_info` VALUES ('2916', '620802', '崆峒区', '311', '28');
INSERT INTO `area_info` VALUES ('2629', '532323', '牟定县', '279', '25');
INSERT INTO `area_info` VALUES ('2530', '522631', '黎平县', '269', '24');
INSERT INTO `area_info` VALUES ('2090', '450223', '鹿寨县', '222', '20');
INSERT INTO `area_info` VALUES ('2040', '441701', '市辖区', '214', '19');
INSERT INTO `area_info` VALUES ('1278', '360681', '贵溪市', '132', '14');
INSERT INTO `area_info` VALUES ('1752', '420984', '汉川市', '179', '17');
INSERT INTO `area_info` VALUES ('2750', '542526', '改则县', '292', '26');
INSERT INTO `area_info` VALUES ('843', '320481', '溧阳市', '80', '10');
INSERT INTO `area_info` VALUES ('1649', '411502', '师河区', '169', '16');
INSERT INTO `area_info` VALUES ('657', '230181', '阿城市', '62', '8');
INSERT INTO `area_info` VALUES ('2272', '510131', '蒲江县', '241', '23');
INSERT INTO `area_info` VALUES ('283', '140725', '寿阳县', '368', '4');
INSERT INTO `area_info` VALUES ('1687', '420107', '青山区', '172', '17');
INSERT INTO `area_info` VALUES ('1198', '350602', '芗城区', '123', '13');
INSERT INTO `area_info` VALUES ('1544', '410381', '偃师市', '157', '16');
INSERT INTO `area_info` VALUES ('2313', '510726', '北川羌族自治县', '246', '23');
INSERT INTO `area_info` VALUES ('2095', '450302', '秀峰区', '223', '20');
INSERT INTO `area_info` VALUES ('1567', '410602', '鹤山区', '160', '16');
INSERT INTO `area_info` VALUES ('1284', '360724', '上犹县', '133', '14');
INSERT INTO `area_info` VALUES ('2240', '500229', '城口县', '239', '22');
INSERT INTO `area_info` VALUES ('907', '321183', '句容市', '87', '10');
INSERT INTO `area_info` VALUES ('1261', '360403', '浔阳区', '130', '14');
INSERT INTO `area_info` VALUES ('523', '210802', '站前区', '46', '6');
INSERT INTO `area_info` VALUES ('2777', '610222', '宜君县', '295', '27');
INSERT INTO `area_info` VALUES ('997', '330901', '市辖区', '98', '11');
INSERT INTO `area_info` VALUES ('2314', '510727', '平武县', '246', '23');
INSERT INTO `area_info` VALUES ('1129', '341622', '蒙城县', '115', '12');
INSERT INTO `area_info` VALUES ('837', '320401', '市辖区', '80', '10');
INSERT INTO `area_info` VALUES ('1632', '411326', '淅川县', '167', '16');
INSERT INTO `area_info` VALUES ('1653', '411523', '新　县', '169', '16');
INSERT INTO `area_info` VALUES ('196', '130984', '河间市', '13', '3');
INSERT INTO `area_info` VALUES ('2036', '441622', '龙川县', '213', '19');
INSERT INTO `area_info` VALUES ('269', '140581', '高平市', '20', '4');
INSERT INTO `area_info` VALUES ('1557', '410502', '文峰区', '159', '16');
INSERT INTO `area_info` VALUES ('2401', '511921', '通江县', '257', '23');
INSERT INTO `area_info` VALUES ('1228', '350901', '市辖区', '126', '13');
INSERT INTO `area_info` VALUES ('985', '330727', '磐安县', '96', '11');
INSERT INTO `area_info` VALUES ('156', '130722', '张北县', '11', '3');
INSERT INTO `area_info` VALUES ('927', '330108', '滨江区', '90', '11');
INSERT INTO `area_info` VALUES ('2562', '530181', '安宁市', '271', '25');
INSERT INTO `area_info` VALUES ('692', '230405', '兴安区', '65', '8');
INSERT INTO `area_info` VALUES ('2356', '511325', '西充县', '251', '23');
INSERT INTO `area_info` VALUES ('2045', '441801', '市辖区', '215', '19');
INSERT INTO `area_info` VALUES ('931', '330127', '淳安县', '90', '11');
INSERT INTO `area_info` VALUES ('1039', '340302', '龙子湖区', '103', '12');
INSERT INTO `area_info` VALUES ('2416', '513228', '黑水县', '259', '23');
INSERT INTO `area_info` VALUES ('51', '130129', '赞皇县', '5', '3');
INSERT INTO `area_info` VALUES ('3032', '640324', '同心县', '328', '30');
INSERT INTO `area_info` VALUES ('1237', '350982', '福鼎市', '126', '13');
INSERT INTO `area_info` VALUES ('1954', '440301', '市辖区', '202', '19');
INSERT INTO `area_info` VALUES ('716', '230701', '市辖区', '68', '8');
INSERT INTO `area_info` VALUES ('2278', '510301', '市辖区', '242', '23');
INSERT INTO `area_info` VALUES ('1802', '430122', '望城县', '186', '18');
INSERT INTO `area_info` VALUES ('1255', '360313', '湘东区', '129', '14');
INSERT INTO `area_info` VALUES ('2640', '532523', '屏边苗族自治县', '280', '25');
INSERT INTO `area_info` VALUES ('2790', '610331', '太白县', '296', '27');
INSERT INTO `area_info` VALUES ('3053', '650201', '市辖区', '332', '31');
INSERT INTO `area_info` VALUES ('1537', '410323', '新安县', '157', '16');
INSERT INTO `area_info` VALUES ('599', '220381', '公主岭市', '55', '7');
INSERT INTO `area_info` VALUES ('2687', '540121', '林周县', '287', '26');
INSERT INTO `area_info` VALUES ('2986', '632127', '化隆回族自治县', '319', '29');
INSERT INTO `area_info` VALUES ('1796', '430102', '芙蓉区', '186', '18');
INSERT INTO `area_info` VALUES ('955', '330328', '文成县', '92', '11');
INSERT INTO `area_info` VALUES ('2617', '530829', '西盟佤族自治县', '277', '25');
INSERT INTO `area_info` VALUES ('2016', '441322', '博罗县', '210', '19');
INSERT INTO `area_info` VALUES ('2490', '520422', '普定县', '265', '24');
INSERT INTO `area_info` VALUES ('1516', '410184', '新郑市', '155', '16');
INSERT INTO `area_info` VALUES ('1399', '370634', '长岛县', '143', '15');
INSERT INTO `area_info` VALUES ('1977', '440607', '三水区', '205', '19');
INSERT INTO `area_info` VALUES ('1113', '341402', '居巢区', '113', '12');
INSERT INTO `area_info` VALUES ('2197', '460108', '美兰区', '235', '21');
INSERT INTO `area_info` VALUES ('2348', '511301', '市辖区', '251', '23');
INSERT INTO `area_info` VALUES ('2934', '621022', '环　县', '313', '28');
INSERT INTO `area_info` VALUES ('1084', '341021', '歙　县', '109', '12');
INSERT INTO `area_info` VALUES ('1315', '360922', '万载县', '135', '14');
INSERT INTO `area_info` VALUES ('2002', '440982', '化州市', '208', '19');
INSERT INTO `area_info` VALUES ('454', '152530', '正蓝旗', '37', '5');
INSERT INTO `area_info` VALUES ('200', '131022', '固安县', '14', '3');
INSERT INTO `area_info` VALUES ('2568', '530324', '罗平县', '272', '25');
INSERT INTO `area_info` VALUES ('456', '152921', '阿拉善左旗', '38', '5');
INSERT INTO `area_info` VALUES ('1303', '360823', '峡江县', '134', '14');
INSERT INTO `area_info` VALUES ('348', '141181', '孝义市', '26', '4');
INSERT INTO `area_info` VALUES ('689', '230402', '向阳区', '65', '8');
INSERT INTO `area_info` VALUES ('2891', '620403', '平川区', '307', '28');
INSERT INTO `area_info` VALUES ('1249', '360202', '昌江区', '128', '14');
INSERT INTO `area_info` VALUES ('2050', '441826', '连南瑶族自治县', '215', '19');
INSERT INTO `area_info` VALUES ('2051', '441827', '清新县', '215', '19');
INSERT INTO `area_info` VALUES ('132', '130625', '徐水县', '10', '3');
INSERT INTO `area_info` VALUES ('2293', '510503', '纳溪区', '244', '23');
INSERT INTO `area_info` VALUES ('670', '230223', '依安县', '63', '8');
INSERT INTO `area_info` VALUES ('2180', '451321', '忻城县', '233', '20');
INSERT INTO `area_info` VALUES ('729', '230714', '乌伊岭区', '68', '8');
INSERT INTO `area_info` VALUES ('560', '211303', '龙城区', '51', '6');
INSERT INTO `area_info` VALUES ('664', '230204', '铁锋区', '63', '8');
INSERT INTO `area_info` VALUES ('2951', '621222', '文　县', '315', '28');
INSERT INTO `area_info` VALUES ('2946', '621125', '漳　县', '314', '28');
INSERT INTO `area_info` VALUES ('98', '130431', '鸡泽县', '8', '3');
INSERT INTO `area_info` VALUES ('1307', '360827', '遂川县', '134', '14');
INSERT INTO `area_info` VALUES ('2944', '621123', '渭源县', '314', '28');
INSERT INTO `area_info` VALUES ('1707', '420323', '竹山县', '174', '17');
INSERT INTO `area_info` VALUES ('404', '150701', '市辖区', '33', '5');
INSERT INTO `area_info` VALUES ('265', '140521', '沁水县', '20', '4');
INSERT INTO `area_info` VALUES ('1116', '341423', '含山县', '113', '12');
INSERT INTO `area_info` VALUES ('1295', '360735', '石城县', '133', '14');
INSERT INTO `area_info` VALUES ('2407', '512022', '乐至县', '258', '23');
INSERT INTO `area_info` VALUES ('1990', '440804', '坡头区', '207', '19');
INSERT INTO `area_info` VALUES ('1044', '340322', '五河县', '103', '12');
INSERT INTO `area_info` VALUES ('3', '110103', '崇文区', '365', '1');
INSERT INTO `area_info` VALUES ('1086', '341023', '黟　县', '109', '12');
INSERT INTO `area_info` VALUES ('3175', '', '大堂区', '375', '34');
INSERT INTO `area_info` VALUES ('252', '140421', '长治县', '19', '4');
INSERT INTO `area_info` VALUES ('932', '330182', '建德市', '90', '11');
INSERT INTO `area_info` VALUES ('2189', '451423', '龙州县', '234', '20');
INSERT INTO `area_info` VALUES ('145', '130638', '雄　县', '10', '3');
INSERT INTO `area_info` VALUES ('2683', '533422', '德钦县', '286', '25');
INSERT INTO `area_info` VALUES ('7', '110107', '石景山区', '365', '1');
INSERT INTO `area_info` VALUES ('1083', '341004', '徽州区', '109', '12');
INSERT INTO `area_info` VALUES ('732', '230722', '嘉荫县', '68', '8');
INSERT INTO `area_info` VALUES ('1447', '371103', '岚山区', '148', '15');
INSERT INTO `area_info` VALUES ('2496', '522223', '玉屏侗族自治县', '266', '24');
INSERT INTO `area_info` VALUES ('510', '210604', '振安区', '44', '6');
INSERT INTO `area_info` VALUES ('1727', '420606', '樊城区', '176', '17');
INSERT INTO `area_info` VALUES ('2517', '522426', '纳雍县', '268', '24');
INSERT INTO `area_info` VALUES ('2191', '451425', '天等县', '234', '20');
INSERT INTO `area_info` VALUES ('1568', '410603', '山城区', '160', '16');
INSERT INTO `area_info` VALUES ('3002', '632622', '班玛县', '323', '29');
INSERT INTO `area_info` VALUES ('2575', '530421', '江川县', '273', '25');
INSERT INTO `area_info` VALUES ('565', '211382', '凌源市', '51', '6');
INSERT INTO `area_info` VALUES ('780', '232721', '呼玛县', '74', '8');
INSERT INTO `area_info` VALUES ('350', '150101', '市辖区', '27', '5');
INSERT INTO `area_info` VALUES ('1366', '370214', '城阳区', '139', '15');
INSERT INTO `area_info` VALUES ('2001', '440981', '高州市', '208', '19');
INSERT INTO `area_info` VALUES ('946', '330283', '奉化市', '91', '11');
INSERT INTO `area_info` VALUES ('676', '230231', '拜泉县', '63', '8');
INSERT INTO `area_info` VALUES ('40', '130104', '桥西区', '5', '3');
INSERT INTO `area_info` VALUES ('2440', '513401', '西昌市', '261', '23');
INSERT INTO `area_info` VALUES ('179', '130828', '围场满族蒙古族自治县', '12', '3');
INSERT INTO `area_info` VALUES ('847', '320503', '平江区', '81', '10');
INSERT INTO `area_info` VALUES ('636', '222404', '珲春市', '61', '7');
INSERT INTO `area_info` VALUES ('1959', '440307', '龙岗区', '202', '19');
INSERT INTO `area_info` VALUES ('1127', '341602', '谯城区', '115', '12');
INSERT INTO `area_info` VALUES ('3061', '652201', '哈密市', '334', '31');
INSERT INTO `area_info` VALUES ('3115', '653226', '于田县', '341', '31');
INSERT INTO `area_info` VALUES ('2730', '542334', '亚东县', '290', '26');
INSERT INTO `area_info` VALUES ('1995', '440882', '雷州市', '207', '19');
INSERT INTO `area_info` VALUES ('2280', '510303', '贡井区', '242', '23');
INSERT INTO `area_info` VALUES ('223', '140107', '杏花岭区', '16', '4');
INSERT INTO `area_info` VALUES ('151', '130702', '桥东区', '11', '3');
INSERT INTO `area_info` VALUES ('2087', '450205', '柳北区', '222', '20');
INSERT INTO `area_info` VALUES ('667', '230207', '碾子山区', '63', '8');
INSERT INTO `area_info` VALUES ('3014', '632802', '德令哈市', '325', '29');
INSERT INTO `area_info` VALUES ('1089', '341102', '琅琊区', '110', '12');
INSERT INTO `area_info` VALUES ('59', '130184', '新乐市', '5', '3');
INSERT INTO `area_info` VALUES ('1103', '341225', '阜南县', '111', '12');
INSERT INTO `area_info` VALUES ('2658', '532801', '景洪市', '282', '25');
INSERT INTO `area_info` VALUES ('3171', '', '花地玛堂区', '375', '34');
INSERT INTO `area_info` VALUES ('2930', '620982', '敦煌市', '312', '28');
INSERT INTO `area_info` VALUES ('2185', '451401', '市辖区', '234', '20');
INSERT INTO `area_info` VALUES ('2479', '520325', '道真仡佬族苗族自治县', '264', '24');
INSERT INTO `area_info` VALUES ('964', '330481', '海宁市', '93', '11');
INSERT INTO `area_info` VALUES ('1149', '350111', '晋安区', '118', '13');
INSERT INTO `area_info` VALUES ('3161', '', '湾仔区', '372', '33');
INSERT INTO `area_info` VALUES ('1892', '431102', '芝山区', '196', '18');
INSERT INTO `area_info` VALUES ('1381', '370401', '市辖区', '141', '15');
INSERT INTO `area_info` VALUES ('2290', '510422', '盐边县', '243', '23');
INSERT INTO `area_info` VALUES ('2255', '500382', '合川市', '240', '22');
INSERT INTO `area_info` VALUES ('1686', '420106', '武昌区', '172', '17');
INSERT INTO `area_info` VALUES ('1710', '420381', '丹江口市', '174', '17');
INSERT INTO `area_info` VALUES ('1011', '331082', '临海市', '99', '11');
INSERT INTO `area_info` VALUES ('1704', '420303', '张湾区', '174', '17');
INSERT INTO `area_info` VALUES ('790', '310109', '虹口区', '367', '9');
INSERT INTO `area_info` VALUES ('2569', '530325', '富源县', '272', '25');
INSERT INTO `area_info` VALUES ('148', '130683', '安国市', '10', '3');
INSERT INTO `area_info` VALUES ('2174', '451227', '巴马瑶族自治县', '232', '20');
INSERT INTO `area_info` VALUES ('2881', '620111', '红古区', '304', '28');
INSERT INTO `area_info` VALUES ('2158', '451029', '田林县', '230', '20');
INSERT INTO `area_info` VALUES ('1666', '411626', '淮阳县', '170', '16');
INSERT INTO `area_info` VALUES ('2955', '621226', '礼　县', '315', '28');
INSERT INTO `area_info` VALUES ('1790', '422828', '鹤峰县', '184', '17');
INSERT INTO `area_info` VALUES ('167', '130733', '崇礼县', '11', '3');
INSERT INTO `area_info` VALUES ('1910', '431226', '麻阳苗族自治县', '197', '18');
INSERT INTO `area_info` VALUES ('2782', '610322', '凤翔县', '296', '27');
INSERT INTO `area_info` VALUES ('1992', '440823', '遂溪县', '207', '19');
INSERT INTO `area_info` VALUES ('2765', '610113', '雁塔区', '294', '27');
INSERT INTO `area_info` VALUES ('356', '150122', '托克托县', '27', '5');
INSERT INTO `area_info` VALUES ('1837', '430511', '北塔区', '190', '18');
INSERT INTO `area_info` VALUES ('2390', '511801', '市辖区', '256', '23');
INSERT INTO `area_info` VALUES ('2438', '513337', '稻城县', '260', '23');
INSERT INTO `area_info` VALUES ('2713', '542229', '加查县', '289', '26');
INSERT INTO `area_info` VALUES ('2263', '510108', '成华区', '241', '23');
INSERT INTO `area_info` VALUES ('428', '150921', '卓资县', '35', '5');
INSERT INTO `area_info` VALUES ('1709', '420325', '房　县', '174', '17');
INSERT INTO `area_info` VALUES ('839', '320404', '钟楼区', '80', '10');
INSERT INTO `area_info` VALUES ('2488', '520402', '西秀区', '265', '24');
INSERT INTO `area_info` VALUES ('704', '230523', '宝清县', '66', '8');
INSERT INTO `area_info` VALUES ('617', '220622', '靖宇县', '58', '7');
INSERT INTO `area_info` VALUES ('1104', '341226', '颍上县', '111', '12');
INSERT INTO `area_info` VALUES ('1123', '341523', '舒城县', '114', '12');
INSERT INTO `area_info` VALUES ('2531', '522632', '榕江县', '269', '24');
INSERT INTO `area_info` VALUES ('2071', '450102', '兴宁区', '221', '20');
INSERT INTO `area_info` VALUES ('800', '310120', '奉贤区', '367', '9');
INSERT INTO `area_info` VALUES ('1496', '371702', '牡丹区', '154', '15');
INSERT INTO `area_info` VALUES ('394', '150526', '扎鲁特旗', '31', '5');
INSERT INTO `area_info` VALUES ('1445', '371101', '市辖区', '148', '15');
INSERT INTO `area_info` VALUES ('3077', '652823', '尉犁县', '337', '31');
INSERT INTO `area_info` VALUES ('1824', '430407', '石鼓区', '189', '18');
INSERT INTO `area_info` VALUES ('48', '130126', '灵寿县', '5', '3');
INSERT INTO `area_info` VALUES ('1263', '360423', '武宁县', '130', '14');
INSERT INTO `area_info` VALUES ('2060', '445221', '揭东县', '219', '19');
INSERT INTO `area_info` VALUES ('1614', '411104', '召陵区', '165', '16');
INSERT INTO `area_info` VALUES ('2516', '522425', '织金县', '268', '24');
INSERT INTO `area_info` VALUES ('1789', '422827', '来凤县', '184', '17');
INSERT INTO `area_info` VALUES ('2112', '450401', '市辖区', '224', '20');
INSERT INTO `area_info` VALUES ('494', '210403', '东洲区', '42', '6');
INSERT INTO `area_info` VALUES ('2454', '513435', '甘洛县', '261', '23');
INSERT INTO `area_info` VALUES ('2256', '500383', '永川市', '240', '22');
INSERT INTO `area_info` VALUES ('1049', '340404', '谢家集区', '104', '12');
INSERT INTO `area_info` VALUES ('1943', '440201', '市辖区', '201', '19');
INSERT INTO `area_info` VALUES ('2546', '522731', '惠水县', '270', '24');
INSERT INTO `area_info` VALUES ('2136', '450802', '港北区', '228', '20');
INSERT INTO `area_info` VALUES ('1346', '361130', '婺源县', '137', '14');
INSERT INTO `area_info` VALUES ('2857', '610901', '市辖区', '302', '27');
INSERT INTO `area_info` VALUES ('1088', '341101', '市辖区', '110', '12');
INSERT INTO `area_info` VALUES ('2024', '441424', '五华县', '211', '19');
INSERT INTO `area_info` VALUES ('66', '130207', '丰南区', '6', '3');
INSERT INTO `area_info` VALUES ('2945', '621124', '临洮县', '314', '28');
INSERT INTO `area_info` VALUES ('1048', '340403', '田家庵区', '104', '12');
INSERT INTO `area_info` VALUES ('2514', '522423', '黔西县', '268', '24');
INSERT INTO `area_info` VALUES ('1199', '350603', '龙文区', '123', '13');
INSERT INTO `area_info` VALUES ('1476', '371481', '乐陵市', '151', '15');
INSERT INTO `area_info` VALUES ('1801', '430121', '长沙县', '186', '18');
INSERT INTO `area_info` VALUES ('1176', '350424', '宁化县', '121', '13');
INSERT INTO `area_info` VALUES ('973', '330601', '市辖区', '95', '11');
INSERT INTO `area_info` VALUES ('1189', '350521', '惠安县', '122', '13');
INSERT INTO `area_info` VALUES ('424', '150825', '乌拉特后旗', '34', '5');
INSERT INTO `area_info` VALUES ('2138', '450804', '覃塘区', '228', '20');
INSERT INTO `area_info` VALUES ('2729', '542333', '仲巴县', '290', '26');
INSERT INTO `area_info` VALUES ('2198', '460201', '市辖区', '236', '21');
INSERT INTO `area_info` VALUES ('3015', '632821', '乌兰县', '325', '29');
INSERT INTO `area_info` VALUES ('1549', '410411', '湛河区', '158', '16');
INSERT INTO `area_info` VALUES ('1860', '430721', '安乡县', '192', '18');
INSERT INTO `area_info` VALUES ('3034', '640401', '市辖区', '329', '30');
INSERT INTO `area_info` VALUES ('1683', '420103', '江汉区', '172', '17');
INSERT INTO `area_info` VALUES ('535', '210921', '阜新蒙古族自治县', '47', '6');
INSERT INTO `area_info` VALUES ('1852', '430623', '华容县', '191', '18');
INSERT INTO `area_info` VALUES ('1419', '370786', '昌邑市', '144', '15');
INSERT INTO `area_info` VALUES ('2768', '610116', '长安区', '294', '27');
INSERT INTO `area_info` VALUES ('1057', '340521', '当涂县', '105', '12');
INSERT INTO `area_info` VALUES ('986', '330781', '兰溪市', '96', '11');
INSERT INTO `area_info` VALUES ('3158', '240101', '滨湖区', '361', '39');
INSERT INTO `area_info` VALUES ('1968', '440512', '濠江区', '204', '19');
INSERT INTO `area_info` VALUES ('496', '210411', '顺城区', '42', '6');
INSERT INTO `area_info` VALUES ('792', '310112', '闵行区', '367', '9');
INSERT INTO `area_info` VALUES ('1439', '370983', '肥城市', '146', '15');
INSERT INTO `area_info` VALUES ('1434', '370902', '泰山区', '146', '15');
INSERT INTO `area_info` VALUES ('796', '310116', '金山区', '367', '9');
INSERT INTO `area_info` VALUES ('2373', '511527', '筠连县', '253', '23');
INSERT INTO `area_info` VALUES ('245', '140303', '矿　区', '18', '4');
INSERT INTO `area_info` VALUES ('1745', '420901', '市辖区', '179', '17');
INSERT INTO `area_info` VALUES ('825', '320301', '市辖区', '79', '10');
INSERT INTO `area_info` VALUES ('374', '150401', '市辖区', '30', '5');
INSERT INTO `area_info` VALUES ('189', '130927', '南皮县', '13', '3');
INSERT INTO `area_info` VALUES ('2282', '510311', '沿滩区', '242', '23');
INSERT INTO `area_info` VALUES ('1647', '411481', '永城市', '168', '16');
INSERT INTO `area_info` VALUES ('1147', '350104', '仓山区', '118', '13');
INSERT INTO `area_info` VALUES ('2117', '450422', '藤　县', '224', '20');
INSERT INTO `area_info` VALUES ('751', '231001', '市辖区', '71', '8');
INSERT INTO `area_info` VALUES ('3127', '654201', '塔城市', '343', '31');
INSERT INTO `area_info` VALUES ('563', '211324', '喀喇沁左翼蒙古族自治县', '51', '6');
INSERT INTO `area_info` VALUES ('2791', '610401', '市辖区', '297', '27');
INSERT INTO `area_info` VALUES ('513', '210682', '凤城市', '44', '6');
INSERT INTO `area_info` VALUES ('983', '330723', '武义县', '96', '11');
INSERT INTO `area_info` VALUES ('2210', '469031', '昌江黎族自治县', '237', '21');
INSERT INTO `area_info` VALUES ('2032', '441581', '陆丰市', '212', '19');
INSERT INTO `area_info` VALUES ('476', '210204', '沙河口区', '40', '6');
INSERT INTO `area_info` VALUES ('823', '320281', '江阴市', '78', '10');
INSERT INTO `area_info` VALUES ('2866', '610928', '旬阳县', '302', '27');
INSERT INTO `area_info` VALUES ('978', '330682', '上虞市', '95', '11');
INSERT INTO `area_info` VALUES ('869', '320706', '海州区', '83', '10');
INSERT INTO `area_info` VALUES ('928', '330109', '萧山区', '90', '11');
INSERT INTO `area_info` VALUES ('660', '230184', '五常市', '62', '8');
INSERT INTO `area_info` VALUES ('432', '150925', '凉城县', '35', '5');
INSERT INTO `area_info` VALUES ('894', '321002', '广陵区', '86', '10');
INSERT INTO `area_info` VALUES ('160', '130726', '蔚　县', '11', '3');
INSERT INTO `area_info` VALUES ('1681', '420101', '市辖区', '172', '17');
INSERT INTO `area_info` VALUES ('1020', '331127', '景宁畲族自治县', '100', '11');
INSERT INTO `area_info` VALUES ('1890', '431081', '资兴市', '195', '18');
INSERT INTO `area_info` VALUES ('2011', '441283', '高要市', '209', '19');
INSERT INTO `area_info` VALUES ('1000', '330921', '岱山县', '98', '11');
INSERT INTO `area_info` VALUES ('637', '222405', '龙井市', '61', '7');
INSERT INTO `area_info` VALUES ('2257', '500384', '南川市', '240', '22');
INSERT INTO `area_info` VALUES ('1332', '361028', '资溪县', '136', '14');
INSERT INTO `area_info` VALUES ('940', '330211', '镇海区', '91', '11');
INSERT INTO `area_info` VALUES ('173', '130822', '兴隆县', '12', '3');
INSERT INTO `area_info` VALUES ('2236', '500225', '大足县', '239', '22');
INSERT INTO `area_info` VALUES ('2690', '540124', '曲水县', '287', '26');
INSERT INTO `area_info` VALUES ('2492', '520424', '关岭布依族苗族自治县', '265', '24');
INSERT INTO `area_info` VALUES ('1244', '360121', '南昌县', '127', '14');
INSERT INTO `area_info` VALUES ('1506', '410102', '中原区', '155', '16');
INSERT INTO `area_info` VALUES ('2068', '445323', '云安县', '220', '19');
INSERT INTO `area_info` VALUES ('1717', '420525', '远安县', '175', '17');
INSERT INTO `area_info` VALUES ('1161', '350206', '湖里区', '119', '13');
INSERT INTO `area_info` VALUES ('1291', '360731', '于都县', '133', '14');
INSERT INTO `area_info` VALUES ('720', '230705', '西林区', '68', '8');
INSERT INTO `area_info` VALUES ('994', '330824', '开化县', '97', '11');
INSERT INTO `area_info` VALUES ('960', '330402', '秀城区', '93', '11');
INSERT INTO `area_info` VALUES ('3125', '654027', '特克斯县', '342', '31');
INSERT INTO `area_info` VALUES ('1493', '371625', '博兴县', '153', '15');
INSERT INTO `area_info` VALUES ('625', '220723', '乾安县', '59', '7');
INSERT INTO `area_info` VALUES ('2537', '522702', '福泉市', '270', '24');
INSERT INTO `area_info` VALUES ('470', '210123', '康平县', '39', '6');
INSERT INTO `area_info` VALUES ('628', '220802', '洮北区', '60', '7');
INSERT INTO `area_info` VALUES ('3113', '653224', '洛浦县', '341', '31');
INSERT INTO `area_info` VALUES ('1734', '420684', '宜城市', '176', '17');
INSERT INTO `area_info` VALUES ('1620', '411222', '陕　县', '166', '16');
INSERT INTO `area_info` VALUES ('1491', '371623', '无棣县', '153', '15');
INSERT INTO `area_info` VALUES ('1345', '361129', '万年县', '137', '14');
INSERT INTO `area_info` VALUES ('2004', '441201', '市辖区', '209', '19');
INSERT INTO `area_info` VALUES ('2576', '530422', '澄江县', '273', '25');
INSERT INTO `area_info` VALUES ('2140', '450881', '桂平市', '228', '20');
INSERT INTO `area_info` VALUES ('1308', '360828', '万安县', '134', '14');
INSERT INTO `area_info` VALUES ('613', '220582', '集安市', '57', '7');
INSERT INTO `area_info` VALUES ('33', '120115', '宝坻区', '366', '2');
INSERT INTO `area_info` VALUES ('1407', '370701', '市辖区', '144', '15');
INSERT INTO `area_info` VALUES ('41', '130105', '新华区', '5', '3');
INSERT INTO `area_info` VALUES ('3026', '640202', '大武口区', '327', '30');
INSERT INTO `area_info` VALUES ('1947', '440222', '始兴县', '201', '19');
INSERT INTO `area_info` VALUES ('1207', '350629', '华安县', '123', '13');
INSERT INTO `area_info` VALUES ('583', '220183', '德惠市', '53', '7');
INSERT INTO `area_info` VALUES ('1536', '410322', '孟津县', '157', '16');
INSERT INTO `area_info` VALUES ('473', '210201', '市辖区', '40', '6');
INSERT INTO `area_info` VALUES ('2365', '511501', '市辖区', '253', '23');
INSERT INTO `area_info` VALUES ('1829', '430423', '衡山县', '189', '18');
INSERT INTO `area_info` VALUES ('1499', '371723', '成武县', '154', '15');
INSERT INTO `area_info` VALUES ('918', '321322', '沭阳县', '89', '10');
INSERT INTO `area_info` VALUES ('1894', '431121', '祁阳县', '196', '18');
INSERT INTO `area_info` VALUES ('1672', '411721', '西平县', '171', '16');
INSERT INTO `area_info` VALUES ('1779', '421281', '赤壁市', '182', '17');
INSERT INTO `area_info` VALUES ('2171', '451224', '东兰县', '232', '20');
INSERT INTO `area_info` VALUES ('1545', '410401', '市辖区', '158', '16');
INSERT INTO `area_info` VALUES ('495', '210404', '望花区', '42', '6');
INSERT INTO `area_info` VALUES ('29', '120111', '西青区', '366', '2');
INSERT INTO `area_info` VALUES ('2394', '511823', '汉源县', '256', '23');
INSERT INTO `area_info` VALUES ('559', '211302', '双塔区', '51', '6');
INSERT INTO `area_info` VALUES ('1063', '340701', '市辖区', '107', '12');
INSERT INTO `area_info` VALUES ('56', '130181', '辛集市', '5', '3');
INSERT INTO `area_info` VALUES ('1325', '361021', '南城县', '136', '14');
INSERT INTO `area_info` VALUES ('543', '211021', '辽阳县', '48', '6');
INSERT INTO `area_info` VALUES ('2512', '522401', '毕节市', '268', '24');
INSERT INTO `area_info` VALUES ('415', '150783', '扎兰屯市', '33', '5');
INSERT INTO `area_info` VALUES ('2923', '620901', '市辖区', '312', '28');
INSERT INTO `area_info` VALUES ('1746', '420902', '孝南区', '179', '17');
INSERT INTO `area_info` VALUES ('1302', '360822', '吉水县', '134', '14');
INSERT INTO `area_info` VALUES ('598', '220323', '伊通满族自治县', '55', '7');
INSERT INTO `area_info` VALUES ('2477', '520323', '绥阳县', '264', '24');
INSERT INTO `area_info` VALUES ('698', '230502', '尖山区', '66', '8');
INSERT INTO `area_info` VALUES ('487', '210304', '立山区', '41', '6');
INSERT INTO `area_info` VALUES ('691', '230404', '南山区', '65', '8');
INSERT INTO `area_info` VALUES ('1828', '430422', '衡南县', '189', '18');
INSERT INTO `area_info` VALUES ('43', '130108', '裕华区', '5', '3');
INSERT INTO `area_info` VALUES ('2038', '441624', '和平县', '213', '19');
INSERT INTO `area_info` VALUES ('935', '330201', '市辖区', '91', '11');
INSERT INTO `area_info` VALUES ('2497', '522224', '石阡县', '266', '24');
INSERT INTO `area_info` VALUES ('2113', '450403', '万秀区', '224', '20');
INSERT INTO `area_info` VALUES ('1215', '350725', '政和县', '124', '13');
INSERT INTO `area_info` VALUES ('515', '210702', '古塔区', '45', '6');
INSERT INTO `area_info` VALUES ('91', '130424', '成安县', '8', '3');
INSERT INTO `area_info` VALUES ('1708', '420324', '竹溪县', '174', '17');
INSERT INTO `area_info` VALUES ('52', '130130', '无极县', '5', '3');
INSERT INTO `area_info` VALUES ('2697', '542124', '类乌齐县', '288', '26');
INSERT INTO `area_info` VALUES ('215', '131126', '故城县', '15', '3');
INSERT INTO `area_info` VALUES ('810', '320113', '栖霞区', '77', '10');
INSERT INTO `area_info` VALUES ('1835', '430502', '双清区', '190', '18');
INSERT INTO `area_info` VALUES ('3173', '', '望德堂区', '375', '34');
INSERT INTO `area_info` VALUES ('2529', '522630', '台江县', '269', '24');
INSERT INTO `area_info` VALUES ('423', '150824', '乌拉特中旗', '34', '5');
INSERT INTO `area_info` VALUES ('3044', '650101', '市辖区', '331', '31');
INSERT INTO `area_info` VALUES ('77', '130302', '海港区', '7', '3');
INSERT INTO `area_info` VALUES ('1340', '361124', '铅山县', '137', '14');
INSERT INTO `area_info` VALUES ('168', '130801', '市辖区', '12', '3');
INSERT INTO `area_info` VALUES ('2103', '450324', '全州县', '223', '20');
INSERT INTO `area_info` VALUES ('533', '210905', '清河门区', '47', '6');
INSERT INTO `area_info` VALUES ('2025', '441426', '平远县', '211', '19');
INSERT INTO `area_info` VALUES ('174', '130823', '平泉县', '12', '3');
INSERT INTO `area_info` VALUES ('3157', '333110', '落阳县', '360', '57');
INSERT INTO `area_info` VALUES ('2432', '513331', '白玉县', '260', '23');
INSERT INTO `area_info` VALUES ('2299', '510601', '市辖区', '245', '23');
INSERT INTO `area_info` VALUES ('2116', '450421', '苍梧县', '224', '20');
INSERT INTO `area_info` VALUES ('421', '150822', '磴口县', '34', '5');
INSERT INTO `area_info` VALUES ('1136', '341801', '市辖区', '117', '12');
INSERT INTO `area_info` VALUES ('1682', '420102', '江岸区', '172', '17');
INSERT INTO `area_info` VALUES ('479', '210213', '金州区', '40', '6');
INSERT INTO `area_info` VALUES ('1736', '420702', '梁子湖区', '177', '17');
INSERT INTO `area_info` VALUES ('1397', '370612', '牟平区', '143', '15');
INSERT INTO `area_info` VALUES ('226', '140110', '晋源区', '16', '4');
INSERT INTO `area_info` VALUES ('896', '321011', '郊　区', '86', '10');
INSERT INTO `area_info` VALUES ('1006', '331021', '玉环县', '99', '11');
INSERT INTO `area_info` VALUES ('748', '230903', '桃山区', '70', '8');
INSERT INTO `area_info` VALUES ('828', '320304', '九里区', '79', '10');
INSERT INTO `area_info` VALUES ('1692', '420115', '江夏区', '172', '17');
INSERT INTO `area_info` VALUES ('1007', '331022', '三门县', '99', '11');
INSERT INTO `area_info` VALUES ('401', '150625', '杭锦旗', '32', '5');
INSERT INTO `area_info` VALUES ('134', '130627', '唐　县', '10', '3');
INSERT INTO `area_info` VALUES ('2588', '530524', '昌宁县', '274', '25');
INSERT INTO `area_info` VALUES ('2487', '520401', '市辖区', '265', '24');
INSERT INTO `area_info` VALUES ('1119', '341502', '金安区', '114', '12');
INSERT INTO `area_info` VALUES ('1733', '420683', '枣阳市', '176', '17');
INSERT INTO `area_info` VALUES ('1787', '422825', '宣恩县', '184', '17');
INSERT INTO `area_info` VALUES ('107', '130521', '邢台县', '9', '3');
INSERT INTO `area_info` VALUES ('1592', '410823', '武陟县', '162', '16');
INSERT INTO `area_info` VALUES ('947', '330301', '市辖区', '92', '11');
INSERT INTO `area_info` VALUES ('1080', '341001', '市辖区', '109', '12');
INSERT INTO `area_info` VALUES ('791', '310110', '杨浦区', '367', '9');
INSERT INTO `area_info` VALUES ('1294', '360734', '寻乌县', '133', '14');
INSERT INTO `area_info` VALUES ('2271', '510129', '大邑县', '241', '23');
INSERT INTO `area_info` VALUES ('2424', '513323', '丹巴县', '260', '23');
INSERT INTO `area_info` VALUES ('2684', '533423', '维西傈僳族自治县', '286', '25');
INSERT INTO `area_info` VALUES ('901', '321101', '市辖区', '87', '10');
INSERT INTO `area_info` VALUES ('1718', '420526', '兴山县', '175', '17');
INSERT INTO `area_info` VALUES ('32', '120114', '武清区', '366', '2');
INSERT INTO `area_info` VALUES ('1168', '350304', '荔城区', '120', '13');
INSERT INTO `area_info` VALUES ('1833', '430482', '常宁市', '189', '18');
INSERT INTO `area_info` VALUES ('2876', '620101', '市辖区', '304', '28');
INSERT INTO `area_info` VALUES ('2787', '610328', '千阳县', '296', '27');
INSERT INTO `area_info` VALUES ('2985', '632126', '互助土族自治县', '319', '29');
INSERT INTO `area_info` VALUES ('2554', '530121', '呈贡县', '271', '25');
INSERT INTO `area_info` VALUES ('1690', '420113', '汉南区', '172', '17');
INSERT INTO `area_info` VALUES ('504', '210505', '南芬区', '43', '6');
INSERT INTO `area_info` VALUES ('1738', '420704', '鄂城区', '177', '17');
INSERT INTO `area_info` VALUES ('1964', '440404', '金湾区', '203', '19');
INSERT INTO `area_info` VALUES ('673', '230227', '富裕县', '63', '8');
INSERT INTO `area_info` VALUES ('19', '120101', '和平区', '366', '2');
INSERT INTO `area_info` VALUES ('1539', '410325', '嵩　县', '157', '16');
INSERT INTO `area_info` VALUES ('2911', '620722', '民乐县', '310', '28');
INSERT INTO `area_info` VALUES ('1312', '360901', '市辖区', '135', '14');
INSERT INTO `area_info` VALUES ('2547', '522732', '三都水族自治县', '270', '24');
INSERT INTO `area_info` VALUES ('1753', '421001', '市辖区', '180', '17');
INSERT INTO `area_info` VALUES ('1377', '370306', '周村区', '140', '15');
INSERT INTO `area_info` VALUES ('1472', '371425', '齐河县', '151', '15');
INSERT INTO `area_info` VALUES ('79', '130304', '北戴河区', '7', '3');
INSERT INTO `area_info` VALUES ('281', '140723', '和顺县', '368', '4');
INSERT INTO `area_info` VALUES ('1619', '411221', '渑池县', '166', '16');
INSERT INTO `area_info` VALUES ('753', '231003', '阳明区', '71', '8');
INSERT INTO `area_info` VALUES ('2261', '510106', '金牛区', '241', '23');
INSERT INTO `area_info` VALUES ('1392', '370522', '利津县', '142', '15');
INSERT INTO `area_info` VALUES ('2331', '511002', '市中区', '249', '23');
INSERT INTO `area_info` VALUES ('1102', '341222', '太和县', '111', '12');
INSERT INTO `area_info` VALUES ('1316', '360923', '上高县', '135', '14');
INSERT INTO `area_info` VALUES ('472', '210181', '新民市', '39', '6');
INSERT INTO `area_info` VALUES ('871', '320722', '东海县', '83', '10');
INSERT INTO `area_info` VALUES ('2311', '510724', '安　县', '246', '23');
INSERT INTO `area_info` VALUES ('2819', '610602', '宝塔区', '299', '27');
INSERT INTO `area_info` VALUES ('860', '320621', '海安县', '82', '10');
INSERT INTO `area_info` VALUES ('2148', '451001', '市辖区', '230', '20');
INSERT INTO `area_info` VALUES ('124', '130601', '市辖区', '10', '3');
INSERT INTO `area_info` VALUES ('2511', '522328', '安龙县', '267', '24');
INSERT INTO `area_info` VALUES ('2983', '632122', '民和回族土族自治县', '319', '29');
INSERT INTO `area_info` VALUES ('3017', '632823', '天峻县', '325', '29');
INSERT INTO `area_info` VALUES ('190', '130928', '吴桥县', '13', '3');
INSERT INTO `area_info` VALUES ('253', '140423', '襄垣县', '19', '4');
INSERT INTO `area_info` VALUES ('1836', '430503', '大祥区', '190', '18');
INSERT INTO `area_info` VALUES ('1588', '410804', '马村区', '162', '16');
INSERT INTO `area_info` VALUES ('250', '140402', '城　区', '19', '4');
INSERT INTO `area_info` VALUES ('2689', '540123', '尼木县', '287', '26');
INSERT INTO `area_info` VALUES ('2601', '530701', '市辖区', '276', '25');
INSERT INTO `area_info` VALUES ('2879', '620104', '西固区', '304', '28');
INSERT INTO `area_info` VALUES ('937', '330204', '江东区', '91', '11');
INSERT INTO `area_info` VALUES ('2633', '532327', '永仁县', '279', '25');
INSERT INTO `area_info` VALUES ('1221', '350802', '新罗区', '125', '13');
INSERT INTO `area_info` VALUES ('683', '230306', '城子河区', '64', '8');
INSERT INTO `area_info` VALUES ('1602', '410927', '台前县', '163', '16');
INSERT INTO `area_info` VALUES ('1256', '360321', '莲花县', '129', '14');
INSERT INTO `area_info` VALUES ('1220', '350801', '市辖区', '125', '13');
INSERT INTO `area_info` VALUES ('2954', '621225', '西和县', '315', '28');
INSERT INTO `area_info` VALUES ('1825', '430408', '蒸湘区', '189', '18');
INSERT INTO `area_info` VALUES ('1226', '350825', '连城县', '125', '13');
INSERT INTO `area_info` VALUES ('2097', '450304', '象山区', '223', '20');
INSERT INTO `area_info` VALUES ('3137', '654323', '福海县', '344', '31');
INSERT INTO `area_info` VALUES ('2484', '520330', '习水县', '264', '24');
INSERT INTO `area_info` VALUES ('1684', '420104', '乔口区', '172', '17');
INSERT INTO `area_info` VALUES ('1120', '341503', '裕安区', '114', '12');
INSERT INTO `area_info` VALUES ('1280', '360702', '章贡区', '133', '14');
INSERT INTO `area_info` VALUES ('1003', '331002', '椒江区', '99', '11');
INSERT INTO `area_info` VALUES ('1616', '411122', '临颍县', '165', '16');
INSERT INTO `area_info` VALUES ('551', '211202', '银州区', '50', '6');
INSERT INTO `area_info` VALUES ('67', '130208', '丰润区', '6', '3');
INSERT INTO `area_info` VALUES ('1492', '371624', '沾化县', '153', '15');
INSERT INTO `area_info` VALUES ('313', '140929', '岢岚县', '24', '4');
INSERT INTO `area_info` VALUES ('2147', '450981', '北流市', '229', '20');
INSERT INTO `area_info` VALUES ('2998', '632523', '贵德县', '322', '29');
INSERT INTO `area_info` VALUES ('3038', '640424', '泾源县', '329', '30');
INSERT INTO `area_info` VALUES ('2928', '620924', '阿克塞哈萨克族自治县', '312', '28');
INSERT INTO `area_info` VALUES ('3064', '652301', '昌吉市', '335', '31');
INSERT INTO `area_info` VALUES ('1270', '360430', '彭泽县', '130', '14');
INSERT INTO `area_info` VALUES ('208', '131101', '市辖区', '15', '3');
INSERT INTO `area_info` VALUES ('260', '140430', '沁　县', '19', '4');
INSERT INTO `area_info` VALUES ('1180', '350428', '将乐县', '121', '13');
INSERT INTO `area_info` VALUES ('1743', '420822', '沙洋县', '178', '17');
INSERT INTO `area_info` VALUES ('382', '150425', '克什克腾旗', '30', '5');
INSERT INTO `area_info` VALUES ('3057', '650205', '乌尔禾区', '332', '31');
INSERT INTO `area_info` VALUES ('3079', '652825', '且末县', '337', '31');
INSERT INTO `area_info` VALUES ('117', '130531', '广宗县', '9', '3');
INSERT INTO `area_info` VALUES ('2645', '532528', '元阳县', '280', '25');
INSERT INTO `area_info` VALUES ('497', '210421', '抚顺县', '42', '6');
INSERT INTO `area_info` VALUES ('2226', '500109', '北碚区', '238', '22');
INSERT INTO `area_info` VALUES ('731', '230716', '上甘岭区', '68', '8');
INSERT INTO `area_info` VALUES ('2758', '542627', '朗　县', '293', '26');
INSERT INTO `area_info` VALUES ('459', '210101', '市辖区', '39', '6');
INSERT INTO `area_info` VALUES ('2400', '511902', '巴州区', '257', '23');
INSERT INTO `area_info` VALUES ('54', '130132', '元氏县', '5', '3');
INSERT INTO `area_info` VALUES ('3196', '321424', '南通市', '82', '10');
INSERT INTO `area_info` VALUES ('3197', '321524', '南通市', '82', '10');
INSERT INTO `area_info` VALUES ('3198', '321525', '南通经济技术开发区', '82', '10');
INSERT INTO `area_info` VALUES ('3199', '445382', '中山市', '217', '19');
INSERT INTO `area_info` VALUES ('3200', '445383', '横栏镇', '217', '19');
INSERT INTO `area_info` VALUES ('3201', '445384', '南城区', '216', '19');
INSERT INTO `area_info` VALUES ('3202', '321526', '新吴区', '78', '10');
INSERT INTO `area_info` VALUES ('3203', '429022', '硚口区', '172', '17');
INSERT INTO `area_info` VALUES ('3204', '321527', '清江浦区', '84', '10');
INSERT INTO `area_info` VALUES ('3205', '429023', '仙桃市', '390', '17');
INSERT INTO `area_info` VALUES ('3206', '445385', '东城区', '217', '19');
INSERT INTO `area_info` VALUES ('3207', '361182', '高新区', '127', '14');
INSERT INTO `area_info` VALUES ('3208', '445386', '龙华新区', '202', '19');
INSERT INTO `area_info` VALUES ('3209', '513438', '郫都区', '241', '23');
INSERT INTO `area_info` VALUES ('3210', '469040', '五指山市', '391', '21');

-- ----------------------------
-- Table structure for city_info
-- ----------------------------
DROP TABLE IF EXISTS `city_info`;
CREATE TABLE `city_info`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `city_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `province_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of city_info
-- ----------------------------
INSERT INTO `city_info` VALUES ('24', '140900', '忻州市', '4');
INSERT INTO `city_info` VALUES ('182', '421200', '咸宁市', '17');
INSERT INTO `city_info` VALUES ('165', '411100', '漯河市', '16');
INSERT INTO `city_info` VALUES ('36', '152200', '兴安盟', '5');
INSERT INTO `city_info` VALUES ('111', '341200', '阜阳市', '12');
INSERT INTO `city_info` VALUES ('201', '440200', '韶关市', '19');
INSERT INTO `city_info` VALUES ('128', '360200', '景德镇市', '14');
INSERT INTO `city_info` VALUES ('330', '640500', '中卫市', '30');
INSERT INTO `city_info` VALUES ('136', '361000', '抚州市', '14');
INSERT INTO `city_info` VALUES ('307', '620400', '白银市', '28');
INSERT INTO `city_info` VALUES ('114', '341500', '六安市', '12');
INSERT INTO `city_info` VALUES ('277', '530800', '思茅市', '25');
INSERT INTO `city_info` VALUES ('105', '340500', '马鞍山市', '12');
INSERT INTO `city_info` VALUES ('233', '451300', '来宾市', '20');
INSERT INTO `city_info` VALUES ('262', '520100', '贵阳市', '24');
INSERT INTO `city_info` VALUES ('100', '331100', '丽水市', '11');
INSERT INTO `city_info` VALUES ('14', '131000', '廊坊市', '3');
INSERT INTO `city_info` VALUES ('192', '430700', '常德市', '18');
INSERT INTO `city_info` VALUES ('123', '350600', '漳州市', '13');
INSERT INTO `city_info` VALUES ('96', '330700', '金华市', '11');
INSERT INTO `city_info` VALUES ('121', '350400', '三明市', '13');
INSERT INTO `city_info` VALUES ('318', '630100', '西宁市', '29');
INSERT INTO `city_info` VALUES ('241', '510100', '成都市', '23');
INSERT INTO `city_info` VALUES ('312', '620900', '酒泉市', '28');
INSERT INTO `city_info` VALUES ('38', '152900', '阿拉善盟', '5');
INSERT INTO `city_info` VALUES ('248', '510900', '遂宁市', '23');
INSERT INTO `city_info` VALUES ('207', '440800', '湛江市', '19');
INSERT INTO `city_info` VALUES ('157', '410300', '洛阳市', '16');
INSERT INTO `city_info` VALUES ('110', '341100', '滁州市', '12');
INSERT INTO `city_info` VALUES ('97', '330800', '衢州市', '11');
INSERT INTO `city_info` VALUES ('23', '140800', '运城市', '4');
INSERT INTO `city_info` VALUES ('155', '410100', '郑州市', '16');
INSERT INTO `city_info` VALUES ('244', '510500', '泸州市', '23');
INSERT INTO `city_info` VALUES ('175', '420500', '宜昌市', '17');
INSERT INTO `city_info` VALUES ('222', '450200', '柳州市', '20');
INSERT INTO `city_info` VALUES ('371', '820000', '澳门特别行政区', '34');
INSERT INTO `city_info` VALUES ('191', '430600', '岳阳市', '18');
INSERT INTO `city_info` VALUES ('370', '810000', '香港特别行政区', '33');
INSERT INTO `city_info` VALUES ('299', '610600', '延安市', '27');
INSERT INTO `city_info` VALUES ('108', '340800', '安庆市', '12');
INSERT INTO `city_info` VALUES ('8', '130400', '邯郸市', '3');
INSERT INTO `city_info` VALUES ('214', '441700', '阳江市', '19');
INSERT INTO `city_info` VALUES ('78', '320200', '无锡市', '10');
INSERT INTO `city_info` VALUES ('37', '152500', '锡林郭勒盟', '5');
INSERT INTO `city_info` VALUES ('310', '620700', '张掖市', '28');
INSERT INTO `city_info` VALUES ('164', '411000', '许昌市', '16');
INSERT INTO `city_info` VALUES ('309', '620600', '武威市', '28');
INSERT INTO `city_info` VALUES ('122', '350500', '泉州市', '13');
INSERT INTO `city_info` VALUES ('221', '450100', '南宁市', '20');
INSERT INTO `city_info` VALUES ('273', '530400', '玉溪市', '25');
INSERT INTO `city_info` VALUES ('193', '430800', '张家界市', '18');
INSERT INTO `city_info` VALUES ('197', '431200', '怀化市', '18');
INSERT INTO `city_info` VALUES ('171', '411700', '驻马店市', '16');
INSERT INTO `city_info` VALUES ('204', '440500', '汕头市', '19');
INSERT INTO `city_info` VALUES ('152', '371500', '聊城市', '15');
INSERT INTO `city_info` VALUES ('256', '511800', '雅安市', '23');
INSERT INTO `city_info` VALUES ('135', '360900', '宜春市', '14');
INSERT INTO `city_info` VALUES ('150', '371300', '临沂市', '15');
INSERT INTO `city_info` VALUES ('257', '511900', '巴中市', '23');
INSERT INTO `city_info` VALUES ('58', '220600', '白山市', '7');
INSERT INTO `city_info` VALUES ('302', '610900', '安康市', '27');
INSERT INTO `city_info` VALUES ('143', '370600', '烟台市', '15');
INSERT INTO `city_info` VALUES ('210', '441300', '惠州市', '19');
INSERT INTO `city_info` VALUES ('344', '654300', '阿勒泰地区', '31');
INSERT INTO `city_info` VALUES ('125', '350800', '龙岩市', '13');
INSERT INTO `city_info` VALUES ('115', '341600', '亳州市', '12');
INSERT INTO `city_info` VALUES ('183', '421300', '随州市', '17');
INSERT INTO `city_info` VALUES ('242', '510300', '自贡市', '23');
INSERT INTO `city_info` VALUES ('328', '640300', '吴忠市', '30');
INSERT INTO `city_info` VALUES ('109', '341000', '黄山市', '12');
INSERT INTO `city_info` VALUES ('212', '441500', '汕尾市', '19');
INSERT INTO `city_info` VALUES ('389', '653300', '伊犁哈萨克自治州伊宁市', '31');
INSERT INTO `city_info` VALUES ('139', '370200', '青岛市', '15');
INSERT INTO `city_info` VALUES ('89', '321300', '宿迁市', '10');
INSERT INTO `city_info` VALUES ('220', '445300', '云浮市', '19');
INSERT INTO `city_info` VALUES ('90', '330100', '杭州市', '11');
INSERT INTO `city_info` VALUES ('274', '530500', '保山市', '25');
INSERT INTO `city_info` VALUES ('386', '423200', '神农架林区', '17');
INSERT INTO `city_info` VALUES ('211', '441400', '梅州市', '19');
INSERT INTO `city_info` VALUES ('315', '621200', '陇南市', '28');
INSERT INTO `city_info` VALUES ('45', '210700', '锦州市', '6');
INSERT INTO `city_info` VALUES ('83', '320700', '连云港市', '10');
INSERT INTO `city_info` VALUES ('162', '410800', '焦作市', '16');
INSERT INTO `city_info` VALUES ('295', '610200', '铜川市', '27');
INSERT INTO `city_info` VALUES ('166', '411200', '三门峡市', '16');
INSERT INTO `city_info` VALUES ('388', '534300', '瑞丽市', '25');
INSERT INTO `city_info` VALUES ('308', '620500', '天水市', '28');
INSERT INTO `city_info` VALUES ('227', '450700', '钦州市', '20');
INSERT INTO `city_info` VALUES ('228', '450800', '贵港市', '20');
INSERT INTO `city_info` VALUES ('46', '210800', '营口市', '6');
INSERT INTO `city_info` VALUES ('225', '450500', '北海市', '20');
INSERT INTO `city_info` VALUES ('178', '420800', '荆门市', '17');
INSERT INTO `city_info` VALUES ('331', '650100', '乌鲁木齐市', '31');
INSERT INTO `city_info` VALUES ('103', '340300', '蚌埠市', '12');
INSERT INTO `city_info` VALUES ('159', '410500', '安阳市', '16');
INSERT INTO `city_info` VALUES ('73', '231200', '绥化市', '8');
INSERT INTO `city_info` VALUES ('86', '321000', '扬州市', '10');
INSERT INTO `city_info` VALUES ('149', '371200', '莱芜市', '15');
INSERT INTO `city_info` VALUES ('263', '520200', '六盘水市', '24');
INSERT INTO `city_info` VALUES ('133', '360700', '赣州市', '14');
INSERT INTO `city_info` VALUES ('147', '371000', '威海市', '15');
INSERT INTO `city_info` VALUES ('198', '431300', '娄底市', '18');
INSERT INTO `city_info` VALUES ('74', '232700', '大兴安岭地区', '8');
INSERT INTO `city_info` VALUES ('343', '654200', '塔城地区', '31');
INSERT INTO `city_info` VALUES ('9', '130500', '邢台市', '3');
INSERT INTO `city_info` VALUES ('55', '220300', '四平市', '7');
INSERT INTO `city_info` VALUES ('294', '610100', '西安市', '27');
INSERT INTO `city_info` VALUES ('6', '130200', '唐山市', '3');
INSERT INTO `city_info` VALUES ('230', '451000', '百色市', '20');
INSERT INTO `city_info` VALUES ('247', '510800', '广元市', '23');
INSERT INTO `city_info` VALUES ('378', '522300', '毕节市', '24');
INSERT INTO `city_info` VALUES ('300', '610700', '汉中市', '27');
INSERT INTO `city_info` VALUES ('84', '320800', '淮安市', '10');
INSERT INTO `city_info` VALUES ('367', '310000', '上海市', '9');
INSERT INTO `city_info` VALUES ('205', '440600', '佛山市', '19');
INSERT INTO `city_info` VALUES ('365', '110000', '北京市', '1');
INSERT INTO `city_info` VALUES ('95', '330600', '绍兴市', '11');
INSERT INTO `city_info` VALUES ('306', '620300', '金昌市', '28');
INSERT INTO `city_info` VALUES ('249', '511000', '内江市', '23');
INSERT INTO `city_info` VALUES ('187', '430200', '株洲市', '18');
INSERT INTO `city_info` VALUES ('301', '610800', '榆林市', '27');
INSERT INTO `city_info` VALUES ('251', '511300', '南充市', '23');
INSERT INTO `city_info` VALUES ('69', '230800', '佳木斯市', '8');
INSERT INTO `city_info` VALUES ('80', '320400', '常州市', '10');
INSERT INTO `city_info` VALUES ('366', '120000', '天津市', '2');
INSERT INTO `city_info` VALUES ('246', '510700', '绵阳市', '23');
INSERT INTO `city_info` VALUES ('206', '440700', '江门市', '19');
INSERT INTO `city_info` VALUES ('172', '420100', '武汉市', '17');
INSERT INTO `city_info` VALUES ('138', '370100', '济南市', '15');
INSERT INTO `city_info` VALUES ('234', '451400', '崇左市', '20');
INSERT INTO `city_info` VALUES ('380', '522500', '黔东南苗族侗族自治州', '24');
INSERT INTO `city_info` VALUES ('64', '230300', '鸡西市', '8');
INSERT INTO `city_info` VALUES ('381', '522600', '黔西南布依族苗族自治州', '24');
INSERT INTO `city_info` VALUES ('293', '542600', '林芝地区', '26');
INSERT INTO `city_info` VALUES ('377', '531000', '红河州', '25');
INSERT INTO `city_info` VALUES ('63', '230200', '齐齐哈尔市', '8');
INSERT INTO `city_info` VALUES ('341', '653200', '和田地区', '31');
INSERT INTO `city_info` VALUES ('154', '371700', '荷泽市', '15');
INSERT INTO `city_info` VALUES ('21', '140600', '朔州市', '4');
INSERT INTO `city_info` VALUES ('68', '230700', '伊春市', '8');
INSERT INTO `city_info` VALUES ('163', '410900', '濮阳市', '16');
INSERT INTO `city_info` VALUES ('82', '320600', '南通市', '10');
INSERT INTO `city_info` VALUES ('101', '340100', '合肥市', '12');
INSERT INTO `city_info` VALUES ('53', '220100', '长春市', '7');
INSERT INTO `city_info` VALUES ('127', '360100', '南昌市', '14');
INSERT INTO `city_info` VALUES ('219', '445200', '揭阳市', '19');
INSERT INTO `city_info` VALUES ('332', '650200', '克拉玛依市', '31');
INSERT INTO `city_info` VALUES ('129', '360300', '萍乡市', '14');
INSERT INTO `city_info` VALUES ('62', '230100', '哈尔滨市', '8');
INSERT INTO `city_info` VALUES ('379', '522400', '黔南布依族苗族自治州', '24');
INSERT INTO `city_info` VALUES ('15', '131100', '衡水市', '3');
INSERT INTO `city_info` VALUES ('142', '370500', '东营市', '15');
INSERT INTO `city_info` VALUES ('224', '450400', '梧州市', '20');
INSERT INTO `city_info` VALUES ('176', '420600', '襄樊市', '17');
INSERT INTO `city_info` VALUES ('276', '530700', '丽江市', '25');
INSERT INTO `city_info` VALUES ('85', '320900', '盐城市', '10');
INSERT INTO `city_info` VALUES ('181', '421100', '黄冈市', '17');
INSERT INTO `city_info` VALUES ('314', '621100', '定西市', '28');
INSERT INTO `city_info` VALUES ('232', '451200', '河池市', '20');
INSERT INTO `city_info` VALUES ('217', '442000', '中山市', '19');
INSERT INTO `city_info` VALUES ('102', '340200', '芜湖市', '12');
INSERT INTO `city_info` VALUES ('42', '210400', '抚顺市', '6');
INSERT INTO `city_info` VALUES ('264', '520300', '遵义市', '24');
INSERT INTO `city_info` VALUES ('119', '350200', '厦门市', '13');
INSERT INTO `city_info` VALUES ('385', '423100', '天门市', '17');
INSERT INTO `city_info` VALUES ('87', '321100', '镇江市', '10');
INSERT INTO `city_info` VALUES ('329', '640400', '固原市', '30');
INSERT INTO `city_info` VALUES ('94', '330500', '湖州市', '11');
INSERT INTO `city_info` VALUES ('258', '512000', '资阳市', '23');
INSERT INTO `city_info` VALUES ('16', '140100', '太原市', '4');
INSERT INTO `city_info` VALUES ('177', '420700', '鄂州市', '17');
INSERT INTO `city_info` VALUES ('326', '640100', '银川市', '30');
INSERT INTO `city_info` VALUES ('39', '210100', '沈阳市', '6');
INSERT INTO `city_info` VALUES ('184', '422800', '恩施土家族苗族自治州', '17');
INSERT INTO `city_info` VALUES ('61', '222400', '延边朝鲜族自治州', '7');
INSERT INTO `city_info` VALUES ('161', '410700', '新乡市', '16');
INSERT INTO `city_info` VALUES ('26', '141100', '吕梁市', '4');
INSERT INTO `city_info` VALUES ('13', '130900', '沧州市', '3');
INSERT INTO `city_info` VALUES ('229', '450900', '玉林市', '20');
INSERT INTO `city_info` VALUES ('126', '350900', '宁德市', '13');
INSERT INTO `city_info` VALUES ('231', '451100', '贺州市', '20');
INSERT INTO `city_info` VALUES ('60', '220800', '白城市', '7');
INSERT INTO `city_info` VALUES ('44', '210600', '丹东市', '6');
INSERT INTO `city_info` VALUES ('303', '611000', '商洛市', '27');
INSERT INTO `city_info` VALUES ('17', '140200', '大同市', '4');
INSERT INTO `city_info` VALUES ('271', '530100', '昆明市', '25');
INSERT INTO `city_info` VALUES ('72', '231100', '黑河市', '8');
INSERT INTO `city_info` VALUES ('116', '341700', '池州市', '12');
INSERT INTO `city_info` VALUES ('311', '620800', '平凉市', '28');
INSERT INTO `city_info` VALUES ('387', '534200', '昭通市', '25');
INSERT INTO `city_info` VALUES ('10', '130600', '保定市', '3');
INSERT INTO `city_info` VALUES ('106', '340600', '淮北市', '12');
INSERT INTO `city_info` VALUES ('12', '130800', '承德市', '3');
INSERT INTO `city_info` VALUES ('169', '411500', '信阳市', '16');
INSERT INTO `city_info` VALUES ('209', '441200', '肇庆市', '19');
INSERT INTO `city_info` VALUES ('368', '500000', '重庆市', '22');
INSERT INTO `city_info` VALUES ('57', '220500', '通化市', '7');
INSERT INTO `city_info` VALUES ('327', '640200', '石嘴山市', '30');
INSERT INTO `city_info` VALUES ('27', '150100', '呼和浩特市', '5');
INSERT INTO `city_info` VALUES ('333', '652100', '吐鲁番地区', '31');
INSERT INTO `city_info` VALUES ('70', '230900', '七台河市', '8');
INSERT INTO `city_info` VALUES ('99', '331000', '台州市', '11');
INSERT INTO `city_info` VALUES ('34', '150800', '巴彦淖尔市', '5');
INSERT INTO `city_info` VALUES ('20', '140500', '晋城市', '4');
INSERT INTO `city_info` VALUES ('120', '350300', '莆田市', '13');
INSERT INTO `city_info` VALUES ('18', '140300', '阳泉市', '4');
INSERT INTO `city_info` VALUES ('373', '810002', '九龙半岛', '33');
INSERT INTO `city_info` VALUES ('130', '360400', '九江市', '14');
INSERT INTO `city_info` VALUES ('25', '141000', '临汾市', '4');
INSERT INTO `city_info` VALUES ('81', '320500', '苏州市', '10');
INSERT INTO `city_info` VALUES ('195', '431000', '郴州市', '18');
INSERT INTO `city_info` VALUES ('93', '330400', '嘉兴市', '11');
INSERT INTO `city_info` VALUES ('88', '321200', '泰州市', '10');
INSERT INTO `city_info` VALUES ('131', '360500', '新余市', '14');
INSERT INTO `city_info` VALUES ('104', '340400', '淮南市', '12');
INSERT INTO `city_info` VALUES ('5', '130100', '石家庄市', '3');
INSERT INTO `city_info` VALUES ('140', '370300', '淄博市', '15');
INSERT INTO `city_info` VALUES ('170', '411600', '周口市', '16');
INSERT INTO `city_info` VALUES ('255', '511700', '达州市', '23');
INSERT INTO `city_info` VALUES ('297', '610400', '咸阳市', '27');
INSERT INTO `city_info` VALUES ('292', '542500', '阿里地区', '26');
INSERT INTO `city_info` VALUES ('383', '422900', '仙桃市', '17');
INSERT INTO `city_info` VALUES ('11', '130700', '张家口市', '3');
INSERT INTO `city_info` VALUES ('113', '341400', '巢湖市', '12');
INSERT INTO `city_info` VALUES ('153', '371600', '滨州市', '15');
INSERT INTO `city_info` VALUES ('202', '440300', '深圳市', '19');
INSERT INTO `city_info` VALUES ('298', '610500', '渭南市', '27');
INSERT INTO `city_info` VALUES ('384', '423000', '潜江市', '17');
INSERT INTO `city_info` VALUES ('180', '421000', '荆州市', '17');
INSERT INTO `city_info` VALUES ('334', '652200', '哈密地区', '31');
INSERT INTO `city_info` VALUES ('141', '370400', '枣庄市', '15');
INSERT INTO `city_info` VALUES ('137', '361100', '上饶市', '14');
INSERT INTO `city_info` VALUES ('28', '150200', '包头市', '5');
INSERT INTO `city_info` VALUES ('289', '542200', '山南地区', '26');
INSERT INTO `city_info` VALUES ('118', '350100', '福州市', '13');
INSERT INTO `city_info` VALUES ('243', '510400', '攀枝花市', '23');
INSERT INTO `city_info` VALUES ('369', '710000', '台湾省', '32');
INSERT INTO `city_info` VALUES ('304', '620100', '兰州市', '28');
INSERT INTO `city_info` VALUES ('266', '522200', '铜仁地区', '24');
INSERT INTO `city_info` VALUES ('71', '231000', '牡丹江市', '8');
INSERT INTO `city_info` VALUES ('35', '150900', '乌兰察布市', '5');
INSERT INTO `city_info` VALUES ('376', '820002', '离岛', '34');
INSERT INTO `city_info` VALUES ('218', '445100', '潮州市', '19');
INSERT INTO `city_info` VALUES ('92', '330300', '温州市', '11');
INSERT INTO `city_info` VALUES ('50', '211200', '铁岭市', '6');
INSERT INTO `city_info` VALUES ('291', '542400', '那曲地区', '26');
INSERT INTO `city_info` VALUES ('112', '341300', '宿州市', '12');
INSERT INTO `city_info` VALUES ('30', '150400', '赤峰市', '5');
INSERT INTO `city_info` VALUES ('305', '620200', '嘉峪关市', '28');
INSERT INTO `city_info` VALUES ('65', '230400', '鹤岗市', '8');
INSERT INTO `city_info` VALUES ('194', '430900', '益阳市', '18');
INSERT INTO `city_info` VALUES ('235', '460100', '海口市', '21');
INSERT INTO `city_info` VALUES ('146', '370900', '泰安市', '15');
INSERT INTO `city_info` VALUES ('49', '211100', '盘锦市', '6');
INSERT INTO `city_info` VALUES ('188', '430300', '湘潭市', '18');
INSERT INTO `city_info` VALUES ('290', '542300', '日喀则地区', '26');
INSERT INTO `city_info` VALUES ('296', '610300', '宝鸡市', '27');
INSERT INTO `city_info` VALUES ('216', '441900', '东莞市', '19');
INSERT INTO `city_info` VALUES ('288', '542100', '昌都地区', '26');
INSERT INTO `city_info` VALUES ('213', '441600', '河源市', '19');
INSERT INTO `city_info` VALUES ('158', '410400', '平顶山市', '16');
INSERT INTO `city_info` VALUES ('47', '210900', '阜新市', '6');
INSERT INTO `city_info` VALUES ('278', '530900', '临沧市', '25');
INSERT INTO `city_info` VALUES ('144', '370700', '潍坊市', '15');
INSERT INTO `city_info` VALUES ('31', '150500', '通辽市', '5');
INSERT INTO `city_info` VALUES ('186', '430100', '长沙市', '18');
INSERT INTO `city_info` VALUES ('372', '810001', '香港岛', '33');
INSERT INTO `city_info` VALUES ('236', '460200', '三亚市', '21');
INSERT INTO `city_info` VALUES ('199', '433100', '湘西土家族苗族自治州', '18');
INSERT INTO `city_info` VALUES ('375', '820001', '澳门半岛', '34');
INSERT INTO `city_info` VALUES ('272', '530300', '曲靖市', '25');
INSERT INTO `city_info` VALUES ('254', '511600', '广安市', '23');
INSERT INTO `city_info` VALUES ('22', '140700', '晋中市', '4');
INSERT INTO `city_info` VALUES ('203', '440400', '珠海市', '19');
INSERT INTO `city_info` VALUES ('287', '540100', '拉萨市', '26');
INSERT INTO `city_info` VALUES ('196', '431100', '永州市', '18');
INSERT INTO `city_info` VALUES ('156', '410200', '开封市', '16');
INSERT INTO `city_info` VALUES ('51', '211300', '朝阳市', '6');
INSERT INTO `city_info` VALUES ('200', '440100', '广州市', '19');
INSERT INTO `city_info` VALUES ('132', '360600', '鹰潭市', '14');
INSERT INTO `city_info` VALUES ('98', '330900', '舟山市', '11');
INSERT INTO `city_info` VALUES ('265', '520400', '安顺市', '24');
INSERT INTO `city_info` VALUES ('252', '511400', '眉山市', '23');
INSERT INTO `city_info` VALUES ('145', '370800', '济宁市', '15');
INSERT INTO `city_info` VALUES ('7', '130300', '秦皇岛市', '3');
INSERT INTO `city_info` VALUES ('40', '210200', '大连市', '6');
INSERT INTO `city_info` VALUES ('179', '420900', '孝感市', '17');
INSERT INTO `city_info` VALUES ('223', '450300', '桂林市', '20');
INSERT INTO `city_info` VALUES ('151', '371400', '德州市', '15');
INSERT INTO `city_info` VALUES ('59', '220700', '松原市', '7');
INSERT INTO `city_info` VALUES ('148', '371100', '日照市', '15');
INSERT INTO `city_info` VALUES ('167', '411300', '南阳市', '16');
INSERT INTO `city_info` VALUES ('48', '211000', '辽阳市', '6');
INSERT INTO `city_info` VALUES ('66', '230500', '双鸭山市', '8');
INSERT INTO `city_info` VALUES ('245', '510600', '德阳市', '23');
INSERT INTO `city_info` VALUES ('374', '810003', '新界', '33');
INSERT INTO `city_info` VALUES ('189', '430400', '衡阳市', '18');
INSERT INTO `city_info` VALUES ('160', '410600', '鹤壁市', '16');
INSERT INTO `city_info` VALUES ('173', '420200', '黄石市', '17');
INSERT INTO `city_info` VALUES ('33', '150700', '呼伦贝尔市', '5');
INSERT INTO `city_info` VALUES ('41', '210300', '鞍山市', '6');
INSERT INTO `city_info` VALUES ('29', '150300', '乌海市', '5');
INSERT INTO `city_info` VALUES ('56', '220400', '辽源市', '7');
INSERT INTO `city_info` VALUES ('43', '210500', '本溪市', '6');
INSERT INTO `city_info` VALUES ('91', '330200', '宁波市', '11');
INSERT INTO `city_info` VALUES ('52', '211400', '葫芦岛市', '6');
INSERT INTO `city_info` VALUES ('215', '441800', '清远市', '19');
INSERT INTO `city_info` VALUES ('77', '320100', '南京市', '10');
INSERT INTO `city_info` VALUES ('168', '411400', '商丘市', '16');
INSERT INTO `city_info` VALUES ('174', '420300', '十堰市', '17');
INSERT INTO `city_info` VALUES ('226', '450600', '防城港市', '20');
INSERT INTO `city_info` VALUES ('134', '360800', '吉安市', '14');
INSERT INTO `city_info` VALUES ('107', '340700', '铜陵市', '12');
INSERT INTO `city_info` VALUES ('32', '150600', '鄂尔多斯市', '5');
INSERT INTO `city_info` VALUES ('19', '140400', '长治市', '4');
INSERT INTO `city_info` VALUES ('79', '320300', '徐州市', '10');
INSERT INTO `city_info` VALUES ('124', '350700', '南平市', '13');
INSERT INTO `city_info` VALUES ('190', '430500', '邵阳市', '18');
INSERT INTO `city_info` VALUES ('253', '511500', '宜宾市', '23');
INSERT INTO `city_info` VALUES ('250', '511100', '乐山市', '23');
INSERT INTO `city_info` VALUES ('67', '230600', '大庆市', '8');
INSERT INTO `city_info` VALUES ('313', '621000', '庆阳市', '28');
INSERT INTO `city_info` VALUES ('208', '440900', '茂名市', '19');
INSERT INTO `city_info` VALUES ('382', '460300', '东方市', '21');
INSERT INTO `city_info` VALUES ('117', '341800', '宣城市', '12');
INSERT INTO `city_info` VALUES ('54', '220200', '吉林市', '7');
INSERT INTO `city_info` VALUES ('390', '423300', '省直辖县级行政区划', '17');
INSERT INTO `city_info` VALUES ('391', '460400', '省直辖县级行政区划', '21');

-- ----------------------------
-- Table structure for exchange_record
-- ----------------------------
DROP TABLE IF EXISTS `exchange_record`;
CREATE TABLE `exchange_record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `ava_credits` int(11) NULL DEFAULT NULL,
  `con_credits` int(11) NULL DEFAULT NULL,
  `exchange_time` datetime(0) NULL DEFAULT NULL,
  `good_codes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_advertise
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_advertise`;
CREATE TABLE `lysj_advertise_advertise`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `begin_time` datetime(0) NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `image_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `is_jump` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `target_range` int(11) NULL DEFAULT NULL,
  `target_type` int(11) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lysj_advertise_advertise_management
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_advertise_management`;
CREATE TABLE `lysj_advertise_advertise_management`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `advertise_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source_type` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `app_type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_cost_money
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_cost_money`;
CREATE TABLE `lysj_advertise_cost_money`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `click_number` int(11) NULL DEFAULT NULL,
  `day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `on_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `run_number` int(11) NULL DEFAULT NULL,
  `show_number` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lysj_advertise_on_management
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_on_management`;
CREATE TABLE `lysj_advertise_on_management`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `advertise_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `advertise_type` int(11) NULL DEFAULT NULL,
  `click_cost` double NULL DEFAULT NULL,
  `click_number` int(11) NULL DEFAULT NULL,
  `click_rate` double NULL DEFAULT NULL,
  `cost_money` double NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `money` double NULL DEFAULT NULL,
  `on_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `run_number` int(11) NULL DEFAULT NULL,
  `show_cost` double NULL DEFAULT NULL,
  `show_number` int(11) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `strategic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_type` int(11) NULL DEFAULT NULL,
  `run_cost` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_region_city
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_region_city`;
CREATE TABLE `lysj_advertise_region_city`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `city_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `strategic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_strategic_distributors
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_strategic_distributors`;
CREATE TABLE `lysj_advertise_strategic_distributors`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `distributors_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `distributors_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operator_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `strategic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_strategic_management
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_strategic_management`;
CREATE TABLE `lysj_advertise_strategic_management`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `source_type` int(11) NULL DEFAULT NULL,
  `operator_range` int(11) NULL DEFAULT NULL,
  `pay_entrys` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `region_type` int(11) NULL DEFAULT NULL,
  `strategic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_range` int(11) NULL DEFAULT NULL,
  `week_day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_strategic_time
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_strategic_time`;
CREATE TABLE `lysj_advertise_strategic_time`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `strategic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_advertise_target
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_target`;
CREATE TABLE `lysj_advertise_target`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `advertise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `city_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `target_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lysj_advertise_view_log
-- ----------------------------
DROP TABLE IF EXISTS `lysj_advertise_view_log`;
CREATE TABLE `lysj_advertise_view_log`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `advertise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `from_range` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lysj_auth_company
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_company`;
CREATE TABLE `lysj_auth_company`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `alipay_reward` decimal(10, 4) NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cooperation_lev` int(11) NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_prorata` decimal(10, 4) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_role` int(11) NULL DEFAULT NULL,
  `wechat_reward` decimal(10, 4) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `manager_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_company
-- ----------------------------
INSERT INTO `lysj_auth_company` VALUES ('1120253834480979968', '2019-04-22 17:11:42', 1, 'vma', '2019-05-05 17:02:08', '三亚', NULL, '天津市', '斗鱼TV', '王大大', 2, '', 10.0000, '12532525412', '天津市', 3, '93088344895918080', NULL, NULL, -1, '93088344895918080', NULL);

-- ----------------------------
-- Table structure for lysj_auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_permission`;
CREATE TABLE `lysj_auth_permission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort_order` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `visible` int(11) NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `authorized` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_permission
-- ----------------------------
INSERT INTO `lysj_auth_permission` VALUES ('0268697611212512016', '2019-04-25 11:14:53', 1, '修改三级代理商', '2019-08-16 15:16:01', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/update', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('0326425611210384993', '2019-04-24 21:09:41', 1, '三级代理商列表', '2019-08-19 14:45:17', NULL, '', 2, '4404147211210374507', 'distributeList', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118702551437545472', '2019-04-18 10:27:27', 1, '商户管理', '2019-08-22 14:51:57', NULL, 'ico_member', 1, '', '/merchant/top/views', 6, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118710330831278080', '2019-04-18 10:58:22', 1, '商户列表', '2019-04-18 10:58:29', NULL, '', 2, '1118702551437545472', 'merchant', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118718810061410304', '2019-04-18 11:32:04', 1, '移动支付', '2019-05-31 19:37:52', NULL, 'ico_phone', 1, '', '/pay/views', 2, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118718912163352576', '2019-04-18 11:32:28', 1, '支付配置', '2019-04-28 09:43:47', NULL, '', 2, '1118718810061410304', 'pay_config', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118719203902361600', '2019-04-18 11:33:38', 1, '获取商户列表', '2019-04-25 14:56:36', NULL, '', 3, '1118710330831278080', '/merchant/merchant/find_by_company_id', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118719269438361600', '2019-04-18 11:33:53', 1, '商户列表', '2019-04-18 11:33:53', NULL, NULL, 3, '1118718912163352576', '/merchant/list', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118721204585369600', '2019-04-18 11:41:35', 1, '进件资料', '2019-04-30 11:29:22', NULL, '', 2, '1118718810061410304', 'incoming_parts', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118722906751373312', '2019-04-18 11:48:20', 1, '资料填写', '2019-04-30 16:49:20', NULL, '', 3, '1118721204585369600', '/merchant/mch_info/update', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1118775503004766208', '2019-04-18 15:17:20', 1, '注销商户', '2019-07-12 14:18:26', NULL, '', 3, '1118710330831278080', '/merchant/merchant/update', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120138083522711552', '2019-04-22 09:31:45', 1, '一级代理商管理', '2019-05-31 19:38:02', NULL, 'ico_member', 1, '', '/operator/views', 3, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120138324154126336', '2019-04-22 09:32:42', 1, '一级代理商列表', '2019-04-22 09:32:42', NULL, NULL, 2, '1120138083522711552', 'operaList', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120138502072307712', '2019-04-22 09:33:25', 1, '已签约一级代理商', '2019-04-22 09:33:25', NULL, NULL, 2, '1120138083522711552', 'operaSigned', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120138665130070016', '2019-04-22 09:34:04', 1, '待审核一级代理商', '2019-04-22 09:34:04', NULL, NULL, 2, '1120138083522711552', 'operaUnaudit', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120138922018607104', '2019-04-22 09:35:05', 1, '查看一级代理商', '2019-08-16 18:47:54', NULL, '', 3, '1120138502072307712', '/auth/company/opera/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120139014914052096', '2019-04-22 09:35:27', 1, '查看一级代理商', '2019-08-16 18:49:28', NULL, '', 3, '1120138665130070016', '/auth/company/opera/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120139058983604224', '2019-04-22 09:35:37', 1, '查看一级代理商', '2019-08-23 10:07:10', NULL, '', 3, '1120138324154126336', '/auth/company/opera/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120215613533458432', '2019-04-22 14:39:50', 1, '一级代理商详情', '2019-04-22 14:39:50', NULL, NULL, 2, '1120138083522711552', 'operaDetail', NULL, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120215715127889920', '2019-04-22 14:40:14', 1, '查看详情', '2019-04-24 14:31:53', NULL, '', 3, '1120215613533458432', '/auth/company/opera/detail', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120249707625582592', '2019-04-22 16:55:18', 1, '添加一级代理商', '2019-04-22 16:55:18', NULL, NULL, 3, '1120138324154126336', '/auth/company/opera/save_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120249839297368064', '2019-04-22 16:55:50', 1, '修改一级代理商', '2019-04-22 16:55:50', NULL, NULL, 3, '1120138324154126336', '/auth/company/opera/update', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120659294870704128', '2019-04-23 20:02:51', 1, '提交审核', '2019-08-23 15:02:56', NULL, '', 3, '1120138324154126336', '/auth/company/opera/audit', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120883336915587072', '2019-04-24 10:53:07', 1, '业务员管理', '2019-05-31 19:38:47', NULL, 'ico_business', 1, '', '/manager/views', 7, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120883550187556864', '2019-04-24 10:53:58', 1, '业务员列表', '2019-04-24 10:53:58', NULL, NULL, 2, '1120883336915587072', 'manager', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120885208325951488', '2019-04-24 11:00:33', 1, '查看业务员', '2019-04-28 11:33:58', NULL, '', 3, '1120883550187556864', '/auth/user/list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120885301695352832', '2019-04-24 11:00:56', 1, '添加业务员', '2019-04-24 11:00:56', NULL, NULL, 3, '1120883550187556864', '/auth/user/save_user', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120885408629133312', '2019-04-24 11:01:21', 1, '修改业务员', '2019-04-24 11:01:21', NULL, NULL, 3, '1120883550187556864', '/auth/user/update_user', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120890590393929728', '2019-04-24 11:21:57', 1, '禁用或启用', '2019-04-24 11:21:57', NULL, NULL, 3, '1120883550187556864', '/auth/user/disable_or_enable', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120935870153756672', '2019-04-24 14:21:52', 1, '获取二级代理商列表', '2019-08-23 10:44:40', NULL, '', 3, '1120215613533458432', '/auth/company/opera/find_by_parent_id', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120935960939466752', '2019-04-24 14:22:14', 1, '获取业务员列表', '2019-08-23 14:33:54', NULL, '', 3, '1120215613533458432', '/auth/user/list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120976795471056896', '2019-04-24 17:04:29', 1, '重置密码', '2019-04-24 17:04:29', NULL, NULL, 3, '1120215613533458432', '/auth/user/reset_password', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1120984354198261760', '2019-04-24 17:34:32', 1, '获取商户列表', '2019-04-24 17:34:32', NULL, NULL, 3, '1120215613533458432', '/merchant/merchant/find_by_company_id', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121037450744041472', '2019-04-24 21:05:31', 1, '二级代理商管理', '2019-05-31 19:38:24', NULL, 'ico_member', 1, '', '/distribute/agent/views', 5, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038118305271808', '2019-04-24 21:08:10', 1, '已签约二级代理商', '2019-04-24 21:08:10', NULL, NULL, 2, '1121037450744041472', 'distributeSigned', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038157865947136', '2019-04-24 21:08:19', 1, '查看二级代理商', '2019-04-24 21:08:19', NULL, NULL, 3, '1121038118305271808', '/auth/company/distribute/list_rewrite', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038203181207552', '2019-04-24 21:08:30', 1, '待审核二级代理商', '2019-04-24 21:08:30', NULL, NULL, 2, '1121037450744041472', 'distributeUnaudit', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038246659362816', '2019-04-24 21:08:41', 1, '查看二级代理商', '2019-08-16 18:46:28', NULL, '', 3, '1121038203181207552', '/auth/company/distribute/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038288224915456', '2019-04-24 21:08:50', 1, '二级代理商详情', '2019-04-24 21:08:50', NULL, NULL, 2, '1121037450744041472', 'distributeDetail', NULL, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038340116844544', '2019-04-24 21:09:03', 1, '查看二级代理商详情', '2019-08-16 14:36:15', NULL, '', 3, '1121038288224915456', '/auth/company/distribute/detail', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038382567395328', '2019-04-24 21:09:13', 1, '查看商户列表', '2019-08-16 18:46:22', NULL, '', 3, '1121038288224915456', '/auth/company/distribute/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038426041356288', '2019-04-24 21:09:23', 1, '查看业务员列表', '2019-08-23 14:32:53', NULL, '', 3, '1121038288224915456', '/auth/user/list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038499303264256', '2019-04-24 21:09:41', 1, '二级代理商列表', '2019-04-24 21:09:41', NULL, NULL, 2, '1121037450744041472', 'distributeList', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038541992890368', '2019-04-24 21:09:51', 1, '查看二级代理商', '2019-04-24 21:09:51', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/list_rewrite', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121038589027815424', '2019-04-24 21:10:02', 1, '审核二级代理商', '2019-04-24 21:10:02', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/audit', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121251136624529408', '2019-04-25 11:14:37', 1, '添加二级代理商', '2019-04-25 11:14:37', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/save_rewrite', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121251201602686976', '2019-04-25 11:14:53', 1, '修改二级代理商', '2019-04-25 11:14:53', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/update', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121322868307791872', '2019-04-25 15:59:40', 1, '查看商户详情', '2019-04-25 15:59:40', NULL, NULL, 2, '1118702551437545472', 'merchantDetail', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121322967737962496', '2019-04-25 16:00:03', 1, '获取详情', '2019-04-25 16:00:03', NULL, NULL, 3, '1121322868307791872', '/merchant/merchant/find_one', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121377871777107968', '2019-04-25 19:38:13', 1, '商户权限', '2019-05-27 22:04:02', NULL, 'ico_business  ico_merchant', 1, '', '/mctPermission/views', 12, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121378693676142592', '2019-04-25 19:41:29', 1, '角色管理', '2019-04-25 19:41:29', NULL, NULL, 2, '1121377871777107968', 'role', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121378876778483712', '2019-04-25 19:42:13', 1, '分配角色权限', '2019-04-25 19:42:13', NULL, NULL, 3, '1121378693676142592', '/merchant/role/edit_role_perm', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379329096421376', '2019-04-25 19:44:01', 1, '修改角色', '2019-04-25 19:44:01', NULL, NULL, 3, '1121378693676142592', '/merchant/role/update', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379395525808128', '2019-04-25 19:44:17', 1, '查看角色', '2019-04-25 19:44:17', NULL, NULL, 3, '1121378693676142592', '/merchant/role/list', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379476878528512', '2019-04-25 19:44:36', 1, '权限管理', '2019-04-25 19:44:36', NULL, NULL, 2, '1121377871777107968', 'permission', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379545874829312', '2019-04-25 19:44:53', 1, '获取权限菜单树', '2019-04-25 19:44:53', NULL, NULL, 3, '1121379476878528512', '/merchant/permission/find_all_list', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379599054409728', '2019-04-25 19:45:05', 1, '保存权限', '2019-04-25 19:45:41', NULL, '', 3, '1121379476878528512', '/merchant/permission/save', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379636304023552', '2019-04-25 19:45:14', 1, '删除权限', '2019-04-25 19:45:30', NULL, '', 3, '1121379476878528512', '/merchant/permission/delete', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1121379672974823424', '2019-04-25 19:45:23', 1, '修改权限', '2019-04-25 19:45:48', NULL, '', 3, '1121379476878528512', '/merchant/permission/update', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122319136639291392', '2019-04-28 09:58:29', 1, '支付配置保存', '2019-04-28 09:59:04', NULL, '', 3, '1118718912163352576', '/pay/pay_channel/config_save', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122405413829087232', '2019-04-28 15:41:19', 1, '注销业务员', '2019-04-28 15:41:19', NULL, NULL, 3, '1120883550187556864', '/auth/user/cancle', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122415158950498304', '2019-04-28 16:20:02', 1, '业务员详情', '2019-04-28 16:20:02', NULL, NULL, 2, '1120883336915587072', 'managerDetail', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122415447657025536', '2019-04-28 16:21:11', 1, '查看详情', '2019-04-28 16:21:11', NULL, NULL, 3, '1122415158950498304', '/auth/user/detail', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122415553840025600', '2019-04-28 16:21:36', 1, '获取二级代理商列表', '2019-08-23 15:23:52', NULL, '', 3, '1122415158950498304', '/auth/company/distribute/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122415637793214464', '2019-04-28 16:21:56', 1, '获取商户列表', '2019-04-29 14:15:45', NULL, '', 3, '1122415158950498304', '/merchant/merchant/re_list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122416024562569216', '2019-04-28 16:23:28', 1, '获取商户列表', '2019-04-28 16:23:28', NULL, NULL, 3, '1122415158950498304', '/merchant/merchant/find_by_company_id', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122786673026011136', '2019-04-29 16:56:18', 1, '数据中心', '2019-05-16 10:42:01', NULL, 'ico_data', 1, '', '/datacenter/views', 10, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122786743125413888', '2019-04-29 16:56:35', 1, '流水概览', '2019-04-29 17:47:49', NULL, '', 2, '1122786673026011136', 'cashFlow', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1122788129565184000', '2019-04-29 17:02:05', 1, '查看数据', '2019-04-29 17:02:05', NULL, NULL, 3, '1122786743125413888', '/auth/company/distribute/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1123055053884755968', '2019-04-30 10:42:45', 1, '提交进件', '2019-04-30 10:42:45', NULL, NULL, 3, '1118710330831278080', '/merchant/mch_info/save', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1123120569223614464', '2019-04-30 15:03:05', 1, '进件详情', '2019-04-30 15:03:05', NULL, NULL, 2, '1118718810061410304', 'detail', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1124973065952956416', '2019-05-05 17:44:15', 1, '商户支付配置', '2019-05-05 17:44:15', NULL, NULL, 3, '1118718912163352576', '/pay/pay_channel/find_pay_channel', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125335526723948544', '2019-05-06 17:44:32', 1, '第一层统计', '2019-05-06 17:44:32', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/count_data', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125335634244931584', '2019-05-06 17:44:58', 1, '订单曲线图', '2019-05-06 17:44:58', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/order_line_chart_all', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125335754852143104', '2019-05-06 17:45:26', 1, '退款曲线图', '2019-05-06 17:45:26', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/refund_line_chart_all', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125335936314511360', '2019-05-06 17:46:10', 1, '支付方式饼图数据', '2019-05-06 17:46:10', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/pay_type_pie_chart_all', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125336054652604416', '2019-05-06 17:46:38', 1, '终端比例饼图数据', '2019-05-06 17:46:38', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/terminal_ratio_pie_chart_all', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125336283560939520', '2019-05-06 17:47:32', 1, '商户统计列表', '2019-05-06 17:47:32', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/merchant_count_list', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125686023515410432', '2019-05-07 16:57:17', 1, '查询所有支付方式', '2019-05-07 16:57:17', NULL, NULL, 3, '1122786743125413888', '/order/order/find_pay_way', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1125686227446665216', '2019-05-07 16:58:06', 1, '查询流水列表', '2019-05-07 16:58:06', NULL, NULL, 3, '1122786743125413888', '/auth/running_account/merchant_count_detail', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1126039093982138368', '2019-05-08 16:20:15', 1, '流水列表', '2019-05-08 16:20:15', NULL, NULL, 2, '1122786673026011136', 'flowList', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1126039278061752320', '2019-05-08 16:20:59', 1, '查询流水列表', '2019-05-08 16:30:00', NULL, '', 3, '1126039093982138368', '/auth/running_account/merchant_count_detail', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1126055950957023232', '2019-05-08 17:27:14', 1, '查询所有支付方式', '2019-05-08 17:27:14', NULL, NULL, 3, '1126039093982138368', '/order/order/find_pay_way', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1128850623320690688', '2019-05-16 10:32:16', 1, '首页', '2019-08-23 21:30:05', NULL, 'ico_home', 1, '', '/index', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1128850752098406400', '2019-05-16 10:32:47', 1, '首页', '2019-05-16 10:40:52', NULL, '', 2, '1128850623320690688', 'index', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1132695364579758080', '2019-05-27 01:09:54', 1, '查看业务员转移信息', '2019-05-27 01:09:54', NULL, NULL, 3, '1120883550187556864', '/auth/user/manager_info', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1132695442828693504', '2019-05-27 01:10:12', 1, '确认转移信息', '2019-05-27 01:10:12', NULL, NULL, 3, '1120883550187556864', '/auth/user/update_manager_relation', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1132705329000353792', '2019-05-27 01:49:29', 1, '查看详情', '2019-05-27 01:49:29', NULL, NULL, 3, '1123120569223614464', '/merchant/mch_info/detail', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1133011078725373952', '2019-05-27 22:04:26', 1, '功能扩展', '2019-07-02 16:46:06', NULL, 'ico_business', 1, '', '/extension/views', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1133011380987891712', '2019-05-27 22:05:38', 1, '功能扩展', '2019-05-27 22:05:38', NULL, NULL, 2, '1133011078725373952', 'tools', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1133646405127245824', '2019-05-29 16:09:00', 1, '商户小程序参数配置', '2019-07-11 10:50:19', NULL, '', 3, '1118710330831278080', '/merchant/merchant/save_applet_config', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1135801397296631808', '2019-06-04 14:52:10', 1, '流水概览详情', '2019-06-04 14:52:10', NULL, NULL, 2, '1122786673026011136', 'flowDetail', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1135801629900148736', '2019-06-04 14:53:05', 1, '查询流水列表', '2019-08-23 15:25:23', NULL, '', 3, '1135801397296631808', '/order/order/find_pay_way', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1136262002197532672', '2019-06-05 21:22:27', 1, '获取商户支付参数', '2019-06-05 21:22:27', NULL, NULL, 3, '1118710330831278080', '/pay/pay_config/find_config', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1136262106396626944', '2019-06-05 21:22:52', 1, '保存支付参数', '2019-06-05 21:22:52', NULL, NULL, 3, '1118710330831278080', '/pay/pay_config/save', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138423767249326080', '2019-06-11 20:32:32', 1, '支付参数', '2019-06-11 20:32:32', NULL, NULL, 2, '1118718810061410304', 'pay_param', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138424763031621632', '2019-06-11 20:36:29', 1, '获取商户列表', '2019-06-11 20:36:29', NULL, NULL, 3, '1138423767249326080', '/merchant/merchant/find_by_company_id', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138434390536359936', '2019-06-11 21:14:44', 1, '获取服务商支付参数', '2019-06-11 21:14:44', NULL, NULL, 3, '1138423767249326080', '/pay/pay_config/find_top_pay_config', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138434532731654144', '2019-06-11 21:15:18', 1, '保存服务商支付参数', '2019-06-11 21:15:18', NULL, NULL, 3, '1138423767249326080', '/pay/pay_config/save_top_pay_config', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138434613698498560', '2019-06-11 21:15:38', 1, '获取商户支付参数', '2019-06-11 21:15:38', NULL, NULL, 3, '1138423767249326080', '/pay/pay_config/find_config', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1138434712235282432', '2019-06-11 21:16:01', 1, '保存支付参数', '2019-06-11 21:16:01', NULL, NULL, 3, '1138423767249326080', '/pay/pay_config/save', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1141992890368210385', '2019-04-24 21:09:51', 1, '查看三级代理商', '2019-08-16 15:16:17', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1144515850582233088', '2019-06-28 16:00:18', 1, '进件列表', '2019-08-22 16:49:27', NULL, '', 2, '1118718810061410304', 'agentMchInfo', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1144515994153259008', '2019-06-28 16:00:52', 1, '查看列表', '2019-06-28 16:00:52', NULL, NULL, 3, '1144515850582233088', '/merchant/mch_info/find_by_fuwushang', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145612342410055680', '2019-07-01 16:37:22', 1, '服务商管理', '2019-08-23 21:32:17', NULL, 'ico_member', 1, '', '/serviceprovider/views', 2, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145612467224154112', '2019-07-01 16:37:51', 1, '服务商管理', '2019-07-01 20:25:05', NULL, '', 2, '1145612342410055680', 'service_provider', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145677817018028032', '2019-07-01 20:57:32', 1, '获取服务商列表', '2019-07-01 20:57:32', NULL, NULL, 3, '1145612467224154112', '/auth/company/service_provider/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145677864296222720', '2019-07-01 20:57:43', 1, '添加服务商', '2019-07-01 20:57:43', NULL, NULL, 3, '1145612467224154112', '/auth/company/service_provider/save_rewrite', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145677912274866176', '2019-07-01 20:57:55', 1, '修改服务商', '2019-07-01 20:57:55', NULL, NULL, 3, '1145612467224154112', '/auth/company/service_provider/update', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145678566724702208', '2019-07-01 21:00:31', 1, '修改服务商状态', '2019-07-01 21:25:33', NULL, '', 3, '1145612467224154112', '/auth/company/service_provider/update_status', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145875700498771968', '2019-07-02 10:03:51', 1, '进入录入网站资料页面', '2019-07-02 10:03:51', NULL, NULL, 3, '1145612467224154112', '/auth/company/site_info/entry', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145875809642950656', '2019-07-02 10:04:17', 1, '查看网站详情', '2019-07-02 10:04:17', NULL, NULL, 3, '1145612467224154112', '/auth/company/site_info/detail', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1145875901003280384', '2019-07-02 10:04:39', 1, '网站资料修改', '2019-07-02 10:04:39', NULL, NULL, 3, '1145612467224154112', '/auth/company/site_info/update', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1147485829380018176', '2019-07-06 20:41:56', 1, '数据概览', '2019-07-06 20:41:56', NULL, NULL, 3, '1128850752098406400', '/auth/running_account_new/date_view', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1147485883532677120', '2019-07-06 20:42:09', 1, '交易数据', '2019-07-06 20:42:09', NULL, NULL, 3, '1128850752098406400', '/auth/running_account_new/date_transaction', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1147485969557852160', '2019-07-06 20:42:29', 1, '左侧图表', '2019-07-06 20:42:29', NULL, NULL, 3, '1128850752098406400', '/auth/running_account_new/data_statistics_left', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1147486037375553536', '2019-07-06 20:42:45', 1, '右侧图表', '2019-07-06 20:42:45', NULL, NULL, 3, '1128850752098406400', '/auth/running_account_new/data_statistics_right', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1149149804109295616', '2019-07-11 10:53:58', 1, '商户小程序参数查看', '2019-07-11 11:02:46', NULL, '', 3, '1118710330831278080', '/merchant/merchant/find_applet_config', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1150957440698691584', '2019-07-16 10:36:52', 1, '广告管理', '2019-07-16 10:38:03', NULL, 'ico_advertise', 1, '', '/advertisement/views', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1150957853829246976', '2019-07-16 10:38:31', 1, '广告管理', '2019-07-16 14:39:52', NULL, '', 2, '1150957440698691584', 'advManages/index', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151014706185515008', '2019-07-16 14:24:25', 1, '新增广告', '2019-07-16 14:26:12', NULL, '', 3, '1150957853829246976', '/advertise/advertise/save', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151015357279907840', '2019-07-16 14:27:01', 1, '广告列表', '2019-07-16 14:27:01', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151015556421267456', '2019-07-16 14:27:48', 1, '禁用/回复', '2019-07-16 14:27:48', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/isable', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151015638671568896', '2019-07-16 14:28:08', 1, '删除', '2019-07-16 14:28:08', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/delete', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151015948492222464', '2019-07-16 14:29:22', 1, '曝光', '2019-07-16 14:29:22', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/exposure', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151183156131155968', '2019-07-17 01:33:47', 1, '获取广告', '2019-07-17 01:33:47', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/view', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151183290868977664', '2019-07-17 01:34:19', 1, '曝光列表', '2019-07-17 01:34:19', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/view/list', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1151183403611869184', '2019-07-17 01:34:46', 1, '查看广告', '2019-07-17 01:34:46', NULL, NULL, 3, '1150957853829246976', '/advertise/advertise/click', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1156486440567525376', '2019-07-31 16:47:09', 1, '佣金结算', '2019-08-05 10:03:47', NULL, 'ico_commission', 1, '', '/commission/views', 1, 0, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1156486724907782144', '2019-07-31 16:48:16', 1, '佣金结算', '2019-07-31 16:53:58', NULL, '', 2, '1156486440567525376', 'settlement/index', 6, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1157195985552044032', '2019-08-02 15:46:37', 1, '分润设置--配置', '2019-08-02 15:46:37', NULL, NULL, 3, '1120138324154126336', '/auth/company/opera/rate_set', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1157196042720407552', '2019-08-02 15:46:51', 1, '分润设置--详情', '2019-08-02 15:46:51', NULL, NULL, 3, '1120138324154126336', '/auth/company/opera/rate', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1157209307602305024', '2019-08-02 16:39:33', 1, '分润设置--配置', '2019-08-22 11:02:07', NULL, '', 3, '1121038499303264256', '/auth/company/distribute/rate_set', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1157209364426735616', '2019-08-02 16:39:47', 1, '分润设置--详情', '2019-08-22 11:02:20', NULL, '', 3, '1121038499303264256', '/auth/company/distribute/rate', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1157493388131938304', '2019-08-03 11:28:24', 1, '服务商配置', '2019-08-03 11:28:24', NULL, NULL, 3, '1133011380987891712', '/auth/wx_open_config', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1160036842734804992', '2019-08-10 11:55:10', 1, '别名配置', '2019-08-13 14:26:50', NULL, '', 2, '93076167321456640', 'roleNameConfig', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1160036902826598400', '2019-08-10 11:55:25', 1, '获取/设置', '2019-08-10 11:55:25', NULL, NULL, 3, '1160036842734804992', '/auth/level_alias', NULL, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1161466523970822144', '2019-08-14 10:36:13', 1, '查看角色（新）', '2019-08-14 10:36:13', NULL, NULL, 3, '93078534242701312', '/auth/role/re_list', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1161467873278418944', '2019-08-14 10:41:35', 1, '授权', '2019-08-14 10:41:35', NULL, NULL, 3, '93078534242701312', '/auth/role', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1161885115923832832', '2019-08-15 14:19:33', 1, '获取下级列表', '2019-08-15 14:19:33', NULL, NULL, 3, '93078534242701312', '/auth/role/company', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1161914303676538880', '2019-08-15 16:15:32', 1, '获取权限树', '2019-08-15 16:15:32', NULL, NULL, 3, '93078534242701312', '/auth/permission/find_all_list_new', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1162251801741135872', '2019-08-16 14:36:38', 1, '服务商查看二级代理商详情', '2019-08-16 14:36:38', NULL, NULL, 3, '1121038288224915456', '/auth/company/opera/list', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1162252484678684672', '2019-08-16 14:39:21', 1, '查看二级代理商详情', '2019-08-16 14:39:21', NULL, NULL, 3, '1121038499303264256', '/auth/company/opera/list', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1162283424247721984', '2019-08-16 16:42:17', 1, '提交审核', '2019-08-16 16:43:08', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/audit', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1162313327709863936', '2019-08-16 18:41:07', 1, '代理商列表', '2019-08-16 18:41:07', NULL, NULL, 3, '1120215613533458432', '/auth/company/opera/find_by_parent_id', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1162316176017395712', '2019-08-16 18:52:26', 1, '启用/禁用', '2019-08-16 18:53:25', NULL, '', 3, '1120138324154126336', '/auth/company/opera/isable', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1163652373654056960', '2019-08-20 11:22:00', 1, '小程序发布配置列表获取', '2019-08-20 11:22:00', NULL, NULL, 3, '1133011380987891712', '/auth/wx_open_config/applet', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164745742898327552', '2019-08-23 11:46:40', 1, '查看三级代理商列表', '2019-08-23 11:46:40', NULL, NULL, 3, '1121038288224915456', '/auth/company/distribute/find_by_parent_id', 1, 1, 1, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164795373032759296', '2019-08-23 15:03:52', 1, '审核', '2019-08-23 15:03:58', NULL, '', 3, '1120138665130070016', '/auth/company/opera/audit', 2, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164796422031065088', '2019-08-23 15:08:03', 1, '查看三级代理商', '2019-08-23 15:08:03', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/find_by_parent_id', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164797416718647296', '2019-08-23 15:12:00', 1, '审核', '2019-08-23 15:12:19', NULL, '', 3, '1121038203181207552', '/auth/company/distribute/audit', 2, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164798853519425536', '2019-08-23 15:17:42', 1, '审核', '2019-08-23 15:17:42', NULL, NULL, 3, '1812075521121038203', '/auth/company/third_agent/audit', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164800090612613120', '2019-08-23 15:22:37', 1, '获取一级代理列表', '2019-08-23 15:22:37', NULL, NULL, 3, '1122415158950498304', '/auth/company/opera/list', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164800989984636928', '2019-08-23 15:26:12', 1, '查询流水详情', '2019-08-23 15:26:12', NULL, NULL, 3, '1135801397296631808', '/auth/running_account/merchant_count_detail', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164900212410638336', '2019-08-23 22:00:28', 1, '启用/禁用', '2019-08-23 22:00:28', NULL, NULL, 3, '1121038499303264256', '/auth/company/distribute/isable', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1164900332443230208', '2019-08-23 22:00:57', 1, '启用/禁用', '2019-08-23 22:00:57', NULL, NULL, 3, '0326425611210384993', '/auth/company/third_agent/isable', NULL, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1168445441121038340', '2019-04-24 21:09:03', 1, '查看三级代理商详情', '2019-08-23 15:21:02', NULL, '', 3, '2249154561121038288', '/auth/company/distribute/detail', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1267356115720936446', '2019-08-02 16:39:47', 1, '分润设置--详情', '2019-08-16 15:15:54', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/rate', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('1812075521121038203', '2019-04-24 21:08:30', 1, '待审核三级代理商', '2019-04-24 21:08:30', NULL, NULL, 2, '4404147211210374507', 'distributeUnaudit', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('2249154561121038288', '2019-04-24 21:08:50', 1, '三级代理商详情', '2019-08-19 14:45:23', NULL, '', 2, '4404147211210374507', 'distributeDetail', 1, 0, 2, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('2452940811212511366', '2019-04-25 11:14:37', 1, '添加三级代理商', '2019-08-16 16:11:35', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/save_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('2781542411210385890', '2019-04-24 21:10:02', 1, '提交审核', '2019-08-16 15:08:51', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/audit', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('3052718081121038118', '2019-04-24 21:08:10', 1, '已约三级级代理商', '2019-08-19 14:45:30', NULL, '', 2, '4404147211210374507', 'distributeSigned', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('4135628811210384260', '2019-04-24 21:09:23', 1, '查看业务员列表', '2019-08-23 15:19:09', NULL, '', 3, '2249154561121038288', '/auth/user/list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('4404147211210374507', '2019-04-24 21:05:31', 1, '三级代理商管理', '2019-08-16 17:11:48', NULL, 'ico_member', 1, '', '/thirdAgent/views', 5, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('4678684672116225248', '2019-08-16 14:39:21', 1, '查看三级代理商详情', '2019-08-16 15:16:38', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/list', 1, 1, NULL, '0', NULL);
INSERT INTO `lysj_auth_permission` VALUES ('5936281611210382466', '2019-04-24 21:08:41', 1, '查看三级代理商', '2019-08-22 14:30:08', NULL, '', 3, '1812075521121038203', '/auth/company/third_agent/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('6023050241157209307', '2019-08-02 16:39:33', 1, '分润设置--配置', '2019-08-16 15:16:07', NULL, '', 3, '0326425611210384993', '/auth/company/third_agent/rate_set', 1, 1, NULL, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('7395328112103838256', '2019-04-24 21:09:13', 1, '查看商户列表', '2019-08-23 15:18:21', NULL, '', 3, '2249154561121038288', '/merchant/merchant/re_list', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('8659471361121038157', '2019-04-24 21:08:19', 1, '查看三级代理商', '2019-08-16 15:17:03', NULL, '', 3, '3052718081121038118', '/auth/company/third_agent/list_rewrite', 1, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93076167321456640', '2019-01-03 10:43:18', 1, '系统管理', '2019-08-23 21:31:25', NULL, 'ico_system', 1, NULL, '/system/views', 2, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93078534242701312', '2019-01-03 10:43:18', 1, '角色管理', '2019-08-20 10:25:26', NULL, '', 2, '93076167321456640', 'role', 1, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93079281722200064', '2019-01-03 10:43:18', 1, '分配角色权限', '2019-01-03 10:43:18', NULL, NULL, 3, '93078534242701312', '/auth/role/edit_role_perm', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93079364861693952', '2019-01-03 10:43:18', 1, '设置默认或取消角色', '2019-01-03 10:43:18', NULL, NULL, 3, '93078534242701312', '/auth/role/setDefault', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93079494650236928', '2019-01-03 10:43:18', 1, '修改角色', '2019-01-03 10:43:18', NULL, NULL, 3, '93078534242701312', '/auth/role/update', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('93083145556987904', '2019-01-03 10:51:32', 1, '查看角色', '2019-01-03 10:51:32', NULL, NULL, 3, '93078534242701312', '/auth/role/list', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('94620316218691584', '2019-01-07 16:39:42', 1, '权限管理', '2019-01-07 16:39:42', NULL, NULL, 2, '93076167321456640', 'permission', NULL, 0, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('94620846584238080', '2019-01-07 16:41:49', 1, '获取权限菜单树', '2019-01-07 16:41:49', NULL, NULL, 3, '94620316218691584', '/auth/permission/find_all_list_new', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('94620922836684800', '2019-01-07 16:42:07', 1, '保存权限', '2019-01-07 16:42:07', NULL, NULL, 3, '94620316218691584', '/auth/permission/save', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('94621272096378880', '2019-01-07 16:43:30', 1, '删除权限', '2019-01-07 16:43:30', NULL, NULL, 3, '94620316218691584', '/auth/permission/delete', NULL, 1, 1, NULL, NULL);
INSERT INTO `lysj_auth_permission` VALUES ('94660438846869504', '2019-01-07 19:19:08', 1, '修改权限', '2019-01-07 19:19:08', NULL, NULL, 3, '94620316218691584', '/auth/permission/update', NULL, 1, 1, NULL, NULL);

-- ----------------------------
-- Table structure for lysj_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_role`;
CREATE TABLE `lysj_auth_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `default_role` bit(1) NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `kind` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_role
-- ----------------------------
INSERT INTO `lysj_auth_role` VALUES ('10', '2019-01-21 01:55:49', 1, '一级代理商业务员', '2019-08-07 15:33:44', NULL, 2, 2, 1);
INSERT INTO `lysj_auth_role` VALUES ('11', '2019-04-29 16:04:17', -1, '高级二级代理商客服', '2019-01-07 19:42:26', NULL, 1, 3, 2);
INSERT INTO `lysj_auth_role` VALUES ('1125379513551818752', '2019-05-06 20:39:19', 1, '超级管理员', '2019-05-06 20:39:19', b'0', NULL, NULL, NULL);
INSERT INTO `lysj_auth_role` VALUES ('12', '2019-04-29 16:04:19', -1, '高级一级代理商客服', '2019-04-29 19:30:26', NULL, 1, 2, 2);
INSERT INTO `lysj_auth_role` VALUES ('13', '2019-01-21 01:55:50', -1, '普通一级代理商客服', '2019-01-07 19:42:26', NULL, 2, 2, 2);
INSERT INTO `lysj_auth_role` VALUES ('14', '2019-04-29 16:04:16', 1, '三级级代理商管理员', '2019-08-07 15:33:35', b'0', 1, 4, 1);
INSERT INTO `lysj_auth_role` VALUES ('15', '2019-01-21 01:55:47', 1, '三级代理商业务员', '2019-08-07 15:33:47', NULL, 2, 4, 1);
INSERT INTO `lysj_auth_role` VALUES ('2', '2019-04-29 16:14:14', 1, '服务商管理员', '2019-08-07 15:33:16', NULL, 1, 1, 1);
INSERT INTO `lysj_auth_role` VALUES ('3', '2019-04-29 16:14:15', -1, '高级服务商客服', '2019-04-29 19:27:56', NULL, 1, 1, 2);
INSERT INTO `lysj_auth_role` VALUES ('4', '2019-04-29 16:04:16', 1, '二级代理商管理员', '2019-08-07 15:33:35', b'0', 1, 3, 1);
INSERT INTO `lysj_auth_role` VALUES ('5', '2019-01-21 02:55:46', -1, '普通服务商客服', '2019-04-29 19:28:34', b'1', 2, 1, 2);
INSERT INTO `lysj_auth_role` VALUES ('6', '2019-01-21 02:55:45', 1, '服务商业务员', '2019-08-07 15:33:40', NULL, 2, 1, 1);
INSERT INTO `lysj_auth_role` VALUES ('7', '2019-04-29 16:04:18', 1, '一级代理商管理员', '2019-08-07 15:33:24', NULL, 1, 2, 1);
INSERT INTO `lysj_auth_role` VALUES ('9', '2019-01-21 01:55:47', 1, '二级代理商业务员', '2019-08-07 15:33:47', NULL, 2, 3, 1);
INSERT INTO `lysj_auth_role` VALUES ('93083670067286016', '2019-01-21 01:55:48', -1, '普通二级代理商客服', '2019-04-29 19:28:19', NULL, 2, 3, 2);

-- ----------------------------
-- Table structure for lysj_auth_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_role_permission`;
CREATE TABLE `lysj_auth_role_permission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `permission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_role_permission
-- ----------------------------
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319144681472', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118719269438361600', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319148875776', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1147486037375553536', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319153070080', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120885301695352832', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319161458688', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1147485969557852160', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319165652992', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '93079281722200064', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319174041600', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1147485883532677120', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319178235904', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '93079364861693952', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319182430208', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1121322967737962496', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319190818816', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1136262106396626944', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319195013120', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122415447657025536', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319199207424', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1144515994153259008', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319207596032', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '93079494650236928', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319215984640', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122416024562569216', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319220178944', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1132695442828693504', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319228567552', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120885408629133312', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319232761856', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120890590393929728', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319241150464', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1128850623320690688', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319245344768', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1124973065952956416', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319249539072', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118775503004766208', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319257927680', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118721204585369600', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319262121984', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122415158950498304', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319274704896', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118719203902361600', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319287287808', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120883550187556864', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319295676416', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118710330831278080', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319299870720', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1121322868307791872', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319304065024', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1132695364579758080', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319312453632', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120885208325951488', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319316647936', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1161885115923832832', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319325036544', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1156486440567525376', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319333425152', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1123055053884755968', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319341813760', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122415637793214464', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319350202368', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '93076167321456640', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319354396672', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1132705329000353792', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319362785280', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122415553840025600', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319366979584', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118718912163352576', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319371173888', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '93078534242701312', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319379562496', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1147485829380018176', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319383756800', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1161914303676538880', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319387951104', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122405413829087232', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319396339712', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1144515850582233088', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319400534016', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1128850752098406400', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319404728320', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1133646405127245824', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319417311232', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1123120569223614464', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319429894144', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118722906751373312', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319434088448', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1122319136639291392', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319438282752', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1149149804109295616', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319442477056', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1161467873278418944', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319446671360', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1161466523970822144', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319455059968', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1136262002197532672', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319459254272', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118718810061410304', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319467642880', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1118702551437545472', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319471837184', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1156486724907782144', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319476031488', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1120883336915587072', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832319480225792', '2019-08-23 17:30:41', 1, NULL, '2019-08-23 17:30:41', '1164800090612613120', '14');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594853060608', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038499303264256', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594857254912', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138434532731654144', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594861449216', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125335936314511360', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594865643520', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125335634244931584', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594869837824', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1812075521121038203', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594874032128', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138434613698498560', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594878226432', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038589027815424', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594882420736', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121322967737962496', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594886615040', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1147485969557852160', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594890809344', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138434712235282432', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594895003648', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125336283560939520', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594899197952', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038541992890368', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594903392256', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121251201602686976', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594907586560', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038288224915456', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594911780864', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120138502072307712', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594915975168', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120215613533458432', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594920169472', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125336054652604416', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594924363776', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121251136624529408', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594928558080', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1136262106396626944', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594932752384', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1157196042720407552', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594936946688', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1144515994153259008', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594941140992', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125335526723948544', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594949529600', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1162252484678684672', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594953723904', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1147485883532677120', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594957918208', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038203181207552', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594962112512', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1147486037375553536', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594966306816', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120249839297368064', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594970501120', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120138665130070016', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594974695424', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138424763031621632', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594978889728', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038157865947136', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594983084032', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118719269438361600', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594987278336', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125335754852143104', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594991472640', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1128850623320690688', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832594995666944', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038118305271808', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595004055552', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1123055053884755968', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595004055553', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038426041356288', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595008249856', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121322868307791872', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595012444160', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '4135628811210384260', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595016638464', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1136262002197532672', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595020832768', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1122319136639291392', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595025027072', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1168445441121038340', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595029221376', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038340116844544', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595033415680', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120138922018607104', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595037609984', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120935870153756672', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595041804288', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1122788129565184000', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595045998592', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1124973065952956416', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595050192896', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1122786743125413888', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595054387200', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118721204585369600', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595058581504', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1149149804109295616', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595062775808', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120139058983604224', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595066970112', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '5936281611210382466', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595071164416', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1141992890368210385', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595075358720', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '2452940811212511366', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595079553024', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038382567395328', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595083747328', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118722906751373312', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595087941632', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1162251801741135872', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595092135936', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1157195985552044032', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595096330240', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '3052718081121038118', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595100524544', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138434390536359936', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595104718848', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118710330831278080', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595108913152', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118775503004766208', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595113107456', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '0326425611210384993', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595121496064', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '8659471361121038157', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595125690368', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1123120569223614464', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595129884672', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '0268697611212512016', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595134078976', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1267356115720936446', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595138273280', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1157209307602305024', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595142467584', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1144515850582233088', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595150856192', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120215715127889920', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595155050496', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '7395328112103838256', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595163439104', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120935960939466752', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595171827712', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1132705329000353792', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595180216320', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1147485829380018176', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595188604928', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118719203902361600', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595196993536', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '4678684672116225248', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595201187840', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120976795471056896', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595209576448', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120249707625582592', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595213770752', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120139014914052096', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595222159360', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1157209364426735616', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595226353664', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1138423767249326080', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595230547968', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '2249154561121038288', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595234742272', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1128850752098406400', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595238936576', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120138324154126336', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595247325184', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118718912163352576', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595251519488', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1162283424247721984', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595255713792', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '2781542411210385890', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595259908096', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '6023050241157209307', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595264102400', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120659294870704128', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595268296704', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121038246659362816', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595272491008', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1133646405127245824', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595276685312', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120984354198261760', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595280879616', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118718810061410304', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595285073920', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1120138083522711552', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595289268224', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1121037450744041472', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595297656832', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '4404147211210374507', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595301851136', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1118702551437545472', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595306045440', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1122786673026011136', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595310239744', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125686227446665216', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595314434048', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1125686023515410432', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595322822656', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1164798853519425536', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595331211264', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1164797416718647296', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595335405568', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1164745742898327552', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595339599872', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1164796422031065088', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595347988480', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1162313327709863936', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832595352182784', '2019-08-23 17:31:47', 1, NULL, '2019-08-23 17:31:47', '1164795373032759296', '6');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780295823360', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1144515994153259008', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780316794880', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118719269438361600', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780375515136', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1147485883532677120', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780383903744', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1147485969557852160', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780404875264', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1147486037375553536', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780409069568', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1136262106396626944', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780446818304', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1121322967737962496', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780451012608', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1128850623320690688', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780455206912', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1133646405127245824', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780459401216', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1121322868307791872', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780463595520', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1124973065952956416', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780467789824', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118719203902361600', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780471984128', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1123120569223614464', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780476178432', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1136262002197532672', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780480372736', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1132705329000353792', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780484567040', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1144515850582233088', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780488761344', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118721204585369600', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780492955648', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118775503004766208', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780497149952', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1149149804109295616', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780501344256', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118718912163352576', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780509732864', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118710330831278080', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780513927168', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1128850752098406400', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780518121472', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118722906751373312', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780522315776', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1123055053884755968', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780526510080', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1147485829380018176', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780530704384', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1122319136639291392', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780534898688', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118718810061410304', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164832780539092992', '2019-08-23 17:32:31', 1, NULL, '2019-08-23 17:32:31', '1118702551437545472', '15');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438786052096', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '93079494650236928', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438790246400', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '94620922836684800', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438794440704', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379395525808128', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438798635008', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '94620316218691584', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438807023616', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '93079281722200064', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438811217920', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1147485969557852160', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438815412224', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1147485883532677120', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438819606528', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1160036902826598400', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438832189440', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1147486037375553536', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438836383744', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145677864296222720', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438840578048', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121378876778483712', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438844772352', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145677912274866176', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438848966656', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '94620846584238080', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438853160960', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379329096421376', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438857355264', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145875809642950656', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438861549568', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '94660438846869504', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438865743872', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145875901003280384', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438878326784', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379476878528512', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438882521088', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379545874829312', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438886715392', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '94621272096378880', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438890909696', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379636304023552', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438895104000', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1128850752098406400', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438899298304', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145678566724702208', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438903492608', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1128850623320690688', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438907686912', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1161466523970822144', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438911881216', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379672974823424', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438916075520', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145875700498771968', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438924464128', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1161914303676538880', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438928658432', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1161885115923832832', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438932852736', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1147485829380018176', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438937047040', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145677817018028032', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438941241344', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145612467224154112', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438945435648', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1161467873278418944', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438949629952', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '93078534242701312', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438953824256', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1160036842734804992', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438958018560', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121378693676142592', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438962212864', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121379599054409728', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438966407168', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1145612342410055680', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438970601472', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '93076167321456640', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164905438974795776', '2019-08-23 22:21:14', 1, NULL, '2019-08-23 22:21:14', '1121377871777107968', '1125379513551818752');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353935720448', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125336054652604416', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353939914752', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1144515994153259008', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353944109056', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120885408629133312', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353948303360', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125335754852143104', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353952497664', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93079494650236928', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353956691968', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93083145556987904', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353960886272', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038203181207552', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353965080576', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164900212410638336', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353969274880', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164900332443230208', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353973469184', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138434712235282432', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353977663488', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121322967737962496', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353981857792', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138434613698498560', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910353986052096', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125686227446665216', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354002829312', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1133011380987891712', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354007023616', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125335936314511360', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354011217920', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93079281722200064', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354015412224', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038288224915456', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354019606528', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120138665130070016', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354023800832', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151015556421267456', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354027995136', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1163652373654056960', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354032189440', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164800090612613120', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354036383744', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120138502072307712', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354044772352', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1162252484678684672', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354048966656', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138434532731654144', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354053160960', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151015948492222464', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354057355264', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1147485969557852160', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354061549568', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122415447657025536', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354065743872', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120249839297368064', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354069938176', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038589027815424', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354078326784', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038541992890368', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354082521088', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120885301695352832', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354086715392', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121251201602686976', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354090909696', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1147485883532677120', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354095104000', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1132695442828693504', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354099298304', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151183403611869184', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354103492608', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138424763031621632', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354107686912', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125336283560939520', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354111881216', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125335526723948544', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354116075520', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1136262106396626944', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354120269824', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038499303264256', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354124464128', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151015638671568896', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354128658432', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1147486037375553536', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354132852736', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121251136624529408', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354137047040', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164798853519425536', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354141241344', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93079364861693952', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354145435648', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038157865947136', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354149629952', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151183290868977664', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354153824256', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164800989984636928', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354153824257', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1812075521121038203', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354158018560', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120215613533458432', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354162212864', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1157493388131938304', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354166407168', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118719269438361600', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354170601472', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125335634244931584', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354174795776', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120890590393929728', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354178990080', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1157196042720407552', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354183184384', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1133011078725373952', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354187378688', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '4678684672116225248', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354191572992', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '0268697611212512016', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354195767296', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120139058983604224', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354199961600', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1162283424247721984', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354204155904', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1126039278061752320', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354208350208', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '4135628811210384260', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354212544512', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120138324154126336', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354216738816', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120883550187556864', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354220933120', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1168445441121038340', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354220933121', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1161466523970822144', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354225127424', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '2452940811212511366', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354229321728', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1124973065952956416', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354233516032', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1149149804109295616', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354237710336', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1126039093982138368', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354241904640', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1162251801741135872', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354246098944', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1161467873278418944', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354250293248', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1144515850582233088', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354254487552', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120935960939466752', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354258681856', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1161914303676538880', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354262876160', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120659294870704128', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354267070464', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1128850752098406400', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354271264768', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120215715127889920', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354275459072', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '2249154561121038288', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354279653376', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151183156131155968', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354283847680', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1150957440698691584', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354288041984', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '8659471361121038157', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354288041985', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038118305271808', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354292236288', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038426041356288', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354296430592', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1162316176017395712', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354300624896', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1123120569223614464', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354304819200', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138423767249326080', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354309013504', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93078534242701312', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354313207808', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1135801629900148736', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354317402112', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1162313327709863936', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354321596416', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122319136639291392', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354325790720', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1132705329000353792', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354329985024', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1132695364579758080', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354334179328', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1150957853829246976', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354338373632', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1138434390536359936', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354342567936', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122405413829087232', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354342567937', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1157209364426735616', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354346762240', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1157209307602305024', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354350956544', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1156486440567525376', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354355150848', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118719203902361600', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354359345152', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1141992890368210385', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354363539456', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '7395328112103838256', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354367733760', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120139014914052096', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354371928064', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1147485829380018176', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354376122368', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120935870153756672', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354380316672', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1126055950957023232', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354384510976', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120138922018607104', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354388705280', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118721204585369600', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354392899584', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '5936281611210382466', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354397093888', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '0326425611210384993', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354401288192', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1135801397296631808', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354405482496', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122415553840025600', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354405482497', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '6023050241157209307', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354409676800', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118722906751373312', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354413871104', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118718912163352576', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354418065408', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122415158950498304', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354422259712', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '2781542411210385890', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354426454016', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120885208325951488', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354430648320', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1128850623320690688', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354434842624', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038382567395328', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354439036928', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1136262002197532672', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354443231232', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121322868307791872', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354447425536', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122786743125413888', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354451619840', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118710330831278080', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354455814144', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151014706185515008', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354460008448', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1157195985552044032', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354464202752', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122415637793214464', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354464202753', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038340116844544', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354468397056', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '3052718081121038118', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354472591360', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1125686023515410432', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354476785664', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1267356115720936446', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354480979968', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1161885115923832832', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354485174272', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120249707625582592', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354489368576', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121038246659362816', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354493562880', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120984354198261760', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354497757184', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164745742898327552', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354501951488', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122788129565184000', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354506145792', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1151015357279907840', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354510340096', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164797416718647296', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354510340097', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '93076167321456640', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354514534400', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118718810061410304', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354518728704', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1164795373032759296', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354522923008', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120138083522711552', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354527117312', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '4404147211210374507', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354531311616', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1121037450744041472', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354531311617', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1118702551437545472', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354535505920', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1156486724907782144', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354539700224', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1120883336915587072', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910354543894528', '2019-08-23 22:40:46', 1, NULL, '2019-08-23 22:40:46', '1122786673026011136', '2');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566834397184', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121251201602686976', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566838591488', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125336054652604416', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566842785792', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1147485969557852160', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566851174400', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1132695442828693504', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566855368704', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125335526723948544', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566859563008', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '93079494650236928', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566863757312', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120890590393929728', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566863757313', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122415447657025536', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566867951616', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120885408629133312', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566872145920', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125336283560939520', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566876340224', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038589027815424', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566880534528', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120885301695352832', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566884728832', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125686227446665216', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566888923136', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038203181207552', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566893117440', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038541992890368', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566897311744', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1136262106396626944', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566901506048', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '93079364861693952', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566905700352', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1812075521121038203', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566909894656', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125335634244931584', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566914088960', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1147486037375553536', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566918283264', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1164800090612613120', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566922477568', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122416024562569216', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566926671872', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '93079281722200064', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566930866176', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125335754852143104', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566935060480', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038499303264256', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566939254784', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1125335936314511360', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566943449088', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1147485883532677120', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566947643392', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038157865947136', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566951837696', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121322967737962496', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566956032000', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118719269438361600', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566960226304', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038288224915456', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566964420608', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1162252484678684672', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566968614912', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1144515994153259008', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566972809216', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121251136624529408', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566977003520', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118719203902361600', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566981197824', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1161467873278418944', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566985392128', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122415158950498304', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566989586432', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122788129565184000', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566993780736', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1267356115720936446', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910566997975040', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1132695364579758080', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567002169344', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1133646405127245824', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567006363648', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1126039278061752320', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567010557952', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1128850752098406400', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567014752256', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120883550187556864', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567018946560', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122415553840025600', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567023140864', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1141992890368210385', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567027335168', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '6023050241157209307', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567027335169', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '2452940811212511366', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567031529472', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1144515850582233088', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567035723776', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038426041356288', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567039918080', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118722906751373312', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567044112384', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118775503004766208', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567048306688', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1168445441121038340', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567052500992', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '2781542411210385890', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567056695296', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1128850623320690688', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567060889600', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1136262002197532672', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567065083904', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '4135628811210384260', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567069278208', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '8659471361121038157', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567073472512', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1135801397296631808', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567077666816', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1123120569223614464', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567077666817', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '0268697611212512016', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567081861120', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1126055950957023232', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567086055424', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1123055053884755968', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567090249728', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038118305271808', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567094444032', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1157209307602305024', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567098638336', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121322868307791872', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567102832640', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118721204585369600', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567107026944', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122415637793214464', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567111221248', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '4678684672116225248', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567115415552', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120885208325951488', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567119609856', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038340116844544', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567123804160', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1162283424247721984', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567127998464', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1147485829380018176', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567132192768', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122405413829087232', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567136387072', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1161466523970822144', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567140581376', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1149149804109295616', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567144775680', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1161885115923832832', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567148969984', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1164745742898327552', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567153164288', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1132705329000353792', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567157358592', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122319136639291392', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567161552896', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1156486440567525376', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567165747200', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '5936281611210382466', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567165747201', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1161914303676538880', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567169941504', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118710330831278080', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567174135808', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '0326425611210384993', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567178330112', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1124973065952956416', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567182524416', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038382567395328', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567186718720', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1162251801741135872', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567190913024', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122786743125413888', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567195107328', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1126039093982138368', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567199301632', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1157209364426735616', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567199301633', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1135801629900148736', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567207690240', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '93078534242701312', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567211884544', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121038246659362816', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567216078848', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '2249154561121038288', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567220273152', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118718912163352576', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567224467456', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '3052718081121038118', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567228661760', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '7395328112103838256', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567232856064', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '93076167321456640', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567232856065', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118718810061410304', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567237050368', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1121037450744041472', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567241244672', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '4404147211210374507', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567245438976', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1118702551437545472', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567249633280', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1156486724907782144', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567253827584', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1120883336915587072', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567258021888', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1122786673026011136', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567262216192', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1164900212410638336', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910567266410496', '2019-08-23 22:41:37', 1, NULL, '2019-08-23 22:41:37', '1164900332443230208', '7');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639861424128', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120890590393929728', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639869812736', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1147485883532677120', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639869812737', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93079364861693952', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639874007040', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1164800989984636928', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639878201344', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1144515994153259008', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639882395648', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1812075521121038203', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639886589952', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1136262106396626944', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639890784256', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122416024562569216', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639890784257', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125335634244931584', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639894978560', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118719269438361600', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639899172864', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1132695442828693504', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639903367168', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122415447657025536', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639907561472', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1147485969557852160', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639911755776', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125336054652604416', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639911755777', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125686227446665216', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639915950080', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93079494650236928', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639920144384', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125335754852143104', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639924338688', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125335936314511360', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639928532992', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93079281722200064', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639932727296', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1121322967737962496', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639932727297', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125335526723948544', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639936921600', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1147486037375553536', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639941115904', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125336283560939520', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639945310208', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120885301695352832', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639949504512', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120885408629133312', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639953698816', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93083145556987904', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639957893120', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1164800090612613120', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639962087424', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '6023050241157209307', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639966281728', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118718912163352576', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639970476032', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118719203902361600', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639974670336', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1128850623320690688', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639978864640', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '0268697611212512016', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639983058944', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122786743125413888', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639987253248', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1132695364579758080', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639991447552', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '0326425611210384993', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639995641856', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1161914303676538880', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639999836160', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1144515850582233088', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910639999836161', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122415637793214464', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640004030464', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '3052718081121038118', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640008224768', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1161466523970822144', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640012419072', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122319136639291392', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640016613376', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122415158950498304', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640020807680', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1128850752098406400', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640025001984', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1135801629900148736', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640025001985', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118710330831278080', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640029196288', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1124973065952956416', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640033390592', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1161885115923832832', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640037584896', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1132705329000353792', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640041779200', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '2452940811212511366', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640045973504', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1136262002197532672', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640050167808', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '4135628811210384260', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640054362112', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120883550187556864', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640054362113', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120885208325951488', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640058556416', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1133646405127245824', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640062750720', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1126039278061752320', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640066945024', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1125686023515410432', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640071139328', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118775503004766208', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640071139329', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1149149804109295616', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640075333632', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '4678684672116225248', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640079527936', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1121322868307791872', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640083722240', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '5936281611210382466', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640087916544', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122405413829087232', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640092110848', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122415553840025600', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640092110849', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1123055053884755968', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640096305152', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1168445441121038340', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640100499456', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '8659471361121038157', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640104693760', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '2781542411210385890', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640108888064', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1126039093982138368', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640113082368', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1123120569223614464', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640117276672', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1126055950957023232', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640121470976', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118722906751373312', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640125665280', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '7395328112103838256', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640125665281', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1141992890368210385', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640129859584', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1147485829380018176', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640134053888', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1135801397296631808', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640138248192', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1162283424247721984', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640142442496', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1156486440567525376', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640146636800', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118721204585369600', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640150831104', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1267356115720936446', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640155025408', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122788129565184000', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640155025409', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93078534242701312', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640159219712', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '2249154561121038288', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640163414016', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1161467873278418944', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640167608320', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118718810061410304', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640171802624', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '93076167321456640', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640175996928', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '4404147211210374507', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640175996929', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1156486724907782144', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640180191232', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1118702551437545472', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640184385536', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1120883336915587072', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640188579840', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1122786673026011136', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164910640192774144', '2019-08-23 22:41:54', 1, NULL, '2019-08-23 22:41:54', '1164900332443230208', '4');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564759318528', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1147485883532677120', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564767707136', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125686227446665216', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564776095744', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125335936314511360', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564780290048', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1812075521121038203', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564780290049', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1121322967737962496', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564788678656', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1144515994153259008', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564792872960', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125335634244931584', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564797067264', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1147486037375553536', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564801261568', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125336054652604416', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564805455872', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1147485969557852160', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564809650176', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118719269438361600', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564818038784', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125335526723948544', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564822233088', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1136262106396626944', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564822233089', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125335754852143104', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564826427392', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1164800989984636928', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564830621696', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125336283560939520', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564834816000', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118721204585369600', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564843204608', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1128850752098406400', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564847398912', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1122319136639291392', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564851593216', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1147485829380018176', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564855787520', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118719203902361600', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564859981824', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '7395328112103838256', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564868370432', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1135801397296631808', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564872564736', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '2452940811212511366', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564876759040', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1128850623320690688', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564880953344', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1122788129565184000', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564885147648', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1126055950957023232', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564889341952', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1125686023515410432', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564893536256', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '5936281611210382466', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564897730560', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1162283424247721984', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564901924864', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1149149804109295616', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564906119168', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1168445441121038340', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564910313472', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '2781542411210385890', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564914507776', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1267356115720936446', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564918702080', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1123055053884755968', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564922896384', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1124973065952956416', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564927090688', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1135801629900148736', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564931284992', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1126039278061752320', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564935479296', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '0326425611210384993', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564939673600', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '4678684672116225248', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564943867904', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '8659471361121038157', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564952256512', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1133646405127245824', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564956450816', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1122786743125413888', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564960645120', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1141992890368210385', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564964839424', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1126039093982138368', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564969033728', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118710330831278080', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564973228032', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1121322868307791872', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564977422336', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '2249154561121038288', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564981616640', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1136262002197532672', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564985810944', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '4135628811210384260', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564990005248', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '3052718081121038118', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564994199552', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118775503004766208', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913564998393856', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '6023050241157209307', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565006782464', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118722906751373312', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565010976768', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1123120569223614464', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565015171072', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1144515850582233088', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565019365376', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1132705329000353792', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565023559680', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '0268697611212512016', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565027753984', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118718912163352576', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565031948288', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118718810061410304', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565036142592', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '4404147211210374507', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565040336896', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1118702551437545472', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565044531200', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1122786673026011136', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913565048725504', '2019-08-23 22:53:32', 1, NULL, '2019-08-23 22:53:32', '1164900332443230208', '9');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652114087936', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1118719269438361600', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652122476544', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121251136624529408', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652126670848', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125335634244931584', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652130865152', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125335754852143104', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652135059456', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125336283560939520', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652139253760', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1147485969557852160', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652143448064', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1164796422031065088', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652147642368', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1812075521121038203', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652151836672', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038203181207552', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652156030976', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1147486037375553536', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652168613888', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1162252484678684672', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652172808192', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1144515994153259008', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652177002496', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038499303264256', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652181196800', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125686227446665216', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652185391104', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1147485883532677120', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652189585408', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038541992890368', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652193779712', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125335936314511360', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652202168320', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038288224915456', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652206362624', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038157865947136', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652210556928', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1164800989984636928', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652214751232', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121251201602686976', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652218945536', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038589027815424', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652223139840', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125336054652604416', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652227334144', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125335526723948544', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652231528448', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1164745742898327552', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652235722752', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038246659362816', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652239917056', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1122786743125413888', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652239917057', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1126055950957023232', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652244111360', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1135801629900148736', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652252499968', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1141992890368210385', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652256694272', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038118305271808', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652260888576', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1162251801741135872', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652260888577', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '4678684672116225248', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652269277184', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '7395328112103838256', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652273471488', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1132705329000353792', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652273471489', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1126039278061752320', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652277665792', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1122319136639291392', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652281860096', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1168445441121038340', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652286054400', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1144515850582233088', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652290248704', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1118722906751373312', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652294443008', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '2249154561121038288', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652298637312', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1147485829380018176', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652302831616', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '0268697611212512016', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652307025920', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1122788129565184000', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652311220224', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1135801397296631808', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652315414528', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1157209364426735616', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652315414529', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1128850623320690688', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652319608832', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '5936281611210382466', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652323803136', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '6023050241157209307', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652332191744', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1124973065952956416', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652332191745', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '2781542411210385890', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652336386048', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1162283424247721984', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652340580352', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038340116844544', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652344774656', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1118721204585369600', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652348968960', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1126039093982138368', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652353163264', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1125686023515410432', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652357357568', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038426041356288', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652361551872', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '8659471361121038157', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652365746176', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1128850752098406400', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652369940480', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1118718912163352576', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652374134784', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '4135628811210384260', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652378329088', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '0326425611210384993', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652382523392', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1267356115720936446', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652386717696', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '2452940811212511366', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652386717697', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1157209307602305024', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652390912000', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121038382567395328', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652395106304', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '3052718081121038118', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652399300608', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1123120569223614464', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652403494912', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1118718810061410304', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652407689216', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1121037450744041472', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652411883520', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '4404147211210374507', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652416077824', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1122786673026011136', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652420272128', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1164900212410638336', '10');
INSERT INTO `lysj_auth_role_permission` VALUES ('1164913652424466432', '2019-08-23 22:53:52', 1, NULL, '2019-08-23 22:53:52', '1164900332443230208', '10');

-- ----------------------------
-- Table structure for lysj_auth_service_pro
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_service_pro`;
CREATE TABLE `lysj_auth_service_pro`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_auth_service_provider
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_service_provider`;
CREATE TABLE `lysj_auth_service_provider`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_service_provider
-- ----------------------------
INSERT INTO `lysj_auth_service_provider` VALUES ('1145616178243670016', '2019-07-01 16:52:36', -1, NULL, '2019-07-03 14:21:27', '1120253834480979968', 'apikey123AAA', 'appid123AAA', 'secrect123AAA');
INSERT INTO `lysj_auth_service_provider` VALUES ('1145616261513187328', '2019-07-01 16:52:56', 1, NULL, '2019-07-03 14:21:27', '1120253834480979968', '222', '111', '333');
INSERT INTO `lysj_auth_service_provider` VALUES ('1145985208690909184', '2019-07-02 17:19:00', 1, NULL, '2019-07-02 17:19:00', '1145982639763595264', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for lysj_auth_site_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_site_info`;
CREATE TABLE `lysj_auth_site_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bgm_photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `copyright_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `icp_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tech_support` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_qrcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mct_bgm_photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_site_info
-- ----------------------------
INSERT INTO `lysj_auth_site_info` VALUES ('1145892661857914880', '2019-07-02 11:11:15', 1, NULL, '2019-07-02 14:32:12', '1120253834480979968', '', '版权所有copyright123', 'pay.ncjb.vmajy.com', '0591-898845455', '1145902450419732480', '0591-888888888', '0591-888888888', 'vmakj', NULL);

-- ----------------------------
-- Table structure for lysj_auth_sms_message
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_sms_message`;
CREATE TABLE `lysj_auth_sms_message`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `access_key_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sign_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sms_info_template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sms_template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_sms_message
-- ----------------------------
INSERT INTO `lysj_auth_sms_message` VALUES ('1', NULL, 1, NULL, '2019-07-03 14:21:27', '4gIbCYx2LbNjogzz', 'ukBfPMtKOTrcnAxi4dKmTmrZvON9wn', '刷脸支付', 'SMS_164080243', 'SMS_164080244', '1120253834480979968');

-- ----------------------------
-- Table structure for lysj_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_user`;
CREATE TABLE `lysj_auth_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `center_account_info_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qr_code_picture_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `pwd_status` int(11) NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_user
-- ----------------------------
INSERT INTO `lysj_auth_user` VALUES ('93088344895918080', '2019-01-03 11:12:12', 1, '管理员', '2019-05-27 01:05:21', NULL, NULL, '213@qq.com', '$2a$10$kIxJ1daAABfH7aMVGc5c9eQvwpO0SItGsdf/AZmlEgImkdObCjH/e', '18860126894', 'admin', NULL, '1120253834480979968', NULL, 1, NULL, 1, '1', '1120253834480979968');

-- ----------------------------
-- Table structure for lysj_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_user_role`;
CREATE TABLE `lysj_auth_user_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_user_role
-- ----------------------------
INSERT INTO `lysj_auth_user_role` VALUES ('1125382036513755136', '2019-05-06 20:49:21', 1, NULL, '2019-05-06 20:49:21', '2', '1231313131');
INSERT INTO `lysj_auth_user_role` VALUES ('1125386426444288000', '2019-05-06 21:06:47', 1, NULL, '2019-05-06 21:06:47', '7', '1125386426389762048');
INSERT INTO `lysj_auth_user_role` VALUES ('1125387941208788992', '2019-05-06 21:12:49', 1, NULL, '2019-05-06 21:12:49', '4', '1125387941200400384');
INSERT INTO `lysj_auth_user_role` VALUES ('1132692891970441216', '2019-05-27 01:00:04', 1, NULL, '2019-05-27 01:00:04', '7', '1132692891710394368');
INSERT INTO `lysj_auth_user_role` VALUES ('1132695842076102657', '2019-05-27 01:11:47', 1, NULL, '2019-05-27 01:11:47', '2', '1132695842076102656');
INSERT INTO `lysj_auth_user_role` VALUES ('1132699665377136641', '2019-05-27 01:26:59', 1, NULL, '2019-05-27 01:26:59', '4', '1132699665377136640');
INSERT INTO `lysj_auth_user_role` VALUES ('1133294389179846656', '2019-05-28 16:50:12', 1, NULL, '2019-05-28 16:50:12', '7', '1133294388982714368');
INSERT INTO `lysj_auth_user_role` VALUES ('1133556304258928641', '2019-05-29 10:10:58', 1, NULL, '2019-05-29 10:10:58', '4', '1133556304258928640');
INSERT INTO `lysj_auth_user_role` VALUES ('1133595092855164929', '2019-05-29 12:45:05', 1, NULL, '2019-05-29 12:45:05', '4', '1133595092855164928');
INSERT INTO `lysj_auth_user_role` VALUES ('1139172004793028608', '2019-06-13 22:05:45', 1, NULL, '2019-06-13 22:05:45', '10', '1139172004738502656');
INSERT INTO `lysj_auth_user_role` VALUES ('1143436178436001792', '2019-06-25 16:30:04', 1, NULL, '2019-06-25 16:30:04', '9', '1143436178352115712');
INSERT INTO `lysj_auth_user_role` VALUES ('1146309901215137792', '2019-07-03 14:49:13', 1, NULL, '2019-07-03 14:49:13', '4', '1146309901152223232');
INSERT INTO `lysj_auth_user_role` VALUES ('93088393117831168', '2019-01-03 11:12:23', 1, NULL, '2019-01-03 11:12:23', '1125379513551818752', '93088344895918080');

-- ----------------------------
-- Table structure for lysj_auth_yun_horn
-- ----------------------------
DROP TABLE IF EXISTS `lysj_auth_yun_horn`;
CREATE TABLE `lysj_auth_yun_horn`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `horn_serial` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `horn_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_auth_yun_horn
-- ----------------------------
INSERT INTO `lysj_auth_yun_horn` VALUES ('1145984768305766400', '2019-07-02 17:17:15', 1, NULL, '2019-07-02 17:17:15', '1145982639763595264', NULL, NULL);

-- ----------------------------
-- Table structure for lysj_fms_md5_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_fms_md5_info`;
CREATE TABLE `lysj_fms_md5_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_fms_md5_info_relation
-- ----------------------------
DROP TABLE IF EXISTS `lysj_fms_md5_info_relation`;
CREATE TABLE `lysj_fms_md5_info_relation`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `md5info_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rank` int(11) NULL DEFAULT NULL,
  `upload_info_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_fms_rich_text_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_fms_rich_text_info`;
CREATE TABLE `lysj_fms_rich_text_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_fms_upload_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_fms_upload_info`;
CREATE TABLE `lysj_fms_upload_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result` bit(1) NOT NULL,
  `upload_details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uploader_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_applet_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_applet_config`;
CREATE TABLE `lysj_member_applet_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_member_applet_config
-- ----------------------------
INSERT INTO `lysj_member_applet_config` VALUES ('1133646914961686528', '2019-05-29 16:11:01', 1, NULL, '2019-05-29 16:13:20', 'wxfa776fa70d7c9534', 'e9a9230d05e7cba245ae2f0de2020356', '1132700752884678656');

-- ----------------------------
-- Table structure for lysj_member_coupon
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_coupon`;
CREATE TABLE `lysj_member_coupon`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `act_status` int(11) NULL DEFAULT NULL,
  `act_time_end` datetime(0) NULL DEFAULT NULL,
  `act_time_start` datetime(0) NULL DEFAULT NULL,
  `change_inventory` int(11) NULL DEFAULT NULL,
  `claim_upper_limit` int(11) NULL DEFAULT NULL,
  `claimed_time` int(11) NULL DEFAULT NULL,
  `coupon_source_type` int(11) NULL DEFAULT NULL,
  `interrupt` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mini_expend_limit` decimal(10, 2) NULL DEFAULT NULL,
  `money` decimal(10, 2) NULL DEFAULT NULL,
  `photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remind_type` int(11) NULL DEFAULT NULL,
  `store_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total_inventory` int(11) NULL DEFAULT NULL,
  `use_time_day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `use_time_week` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_time_end` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_time_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_type` int(11) NULL DEFAULT NULL,
  `wx_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_type` int(11) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_credits_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_credits_info`;
CREATE TABLE `lysj_member_credits_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `member_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trade_scores` int(11) NULL DEFAULT NULL,
  `trade_time` datetime(0) NULL DEFAULT NULL,
  `transaction_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trade_num` int(11) NULL DEFAULT NULL,
  `ava_credits` int(11) NULL DEFAULT NULL,
  `info_type` int(11) NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_creditsproduct
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_creditsproduct`;
CREATE TABLE `lysj_member_creditsproduct`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `credits` int(11) NULL DEFAULT NULL,
  `exchange_end` datetime(0) NULL DEFAULT NULL,
  `exchange_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exchange_num` int(11) NULL DEFAULT NULL,
  `exchange_start` datetime(0) NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_limited` int(11) NULL DEFAULT NULL,
  `limited_num` int(11) NULL DEFAULT NULL,
  `product_money` decimal(19, 2) NULL DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_nums` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `interrupt` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_exchange_record
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_exchange_record`;
CREATE TABLE `lysj_member_exchange_record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `ava_credits` int(11) NULL DEFAULT NULL,
  `con_credits` int(11) NULL DEFAULT NULL,
  `exchange_time` datetime(0) NULL DEFAULT NULL,
  `good_codes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imageurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_money` decimal(19, 2) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_member
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_member`;
CREATE TABLE `lysj_member_member`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `balance` decimal(19, 2) NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scores` decimal(19, 2) NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `last_pay_date` datetime(0) NULL DEFAULT NULL,
  `channel` int(11) NULL DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `official_open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_member_card
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_member_card`;
CREATE TABLE `lysj_member_member_card`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `accept_way` int(11) NULL DEFAULT NULL,
  `background_picture_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `coupon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `present_scores` int(11) NULL DEFAULT NULL,
  `privilege_explain` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tip` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_member_receive_card
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_member_receive_card`;
CREATE TABLE `lysj_member_member_receive_card`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_person_coupon
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_person_coupon`;
CREATE TABLE `lysj_member_person_coupon`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `claim_upper_limit` int(11) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `coupon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mini_expend_limit` decimal(19, 2) NULL DEFAULT NULL,
  `money` decimal(10, 2) NULL DEFAULT NULL,
  `photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `store_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `use_time_day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `use_time_week` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_time_end` datetime(0) NULL DEFAULT NULL,
  `valid_time_start` datetime(0) NULL DEFAULT NULL,
  `syn_status` int(11) NULL DEFAULT NULL,
  `remind_times` int(11) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_ruler
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_ruler`;
CREATE TABLE `lysj_member_ruler`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `consumption_amount` decimal(19, 2) NOT NULL,
  `credits` int(11) NOT NULL,
  `is_true` int(11) NOT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_store_switch
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_store_switch`;
CREATE TABLE `lysj_member_store_switch`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_stored_recored
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_stored_recored`;
CREATE TABLE `lysj_member_stored_recored`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `gift_money` decimal(10, 2) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operation_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_way` int(11) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `post_trading_money` decimal(10, 2) NULL DEFAULT NULL,
  `source` int(11) NULL DEFAULT NULL,
  `stored_money` decimal(10, 2) NULL DEFAULT NULL,
  `stored_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trade_type` int(11) NULL DEFAULT NULL,
  `trading_money` decimal(10, 2) NULL DEFAULT NULL,
  `discount_money` decimal(10, 2) NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `coupon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_status` int(11) NULL DEFAULT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `store_rule_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scores` int(11) NULL DEFAULT NULL,
  `remain_score` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_member_stored_rule
-- ----------------------------
DROP TABLE IF EXISTS `lysj_member_stored_rule`;
CREATE TABLE `lysj_member_stored_rule`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `gift_amount` int(11) NULL DEFAULT NULL,
  `gift_type` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `stored_amount` int(11) NULL DEFAULT NULL,
  `stored_money` decimal(10, 2) NULL DEFAULT NULL,
  `coupon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gift_money` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_blank_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_blank_qr_code`;
CREATE TABLE `lysj_merchant_blank_qr_code`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `mch_qr_code_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_hsf_mch_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_hsf_mch_info`;
CREATE TABLE `lysj_merchant_hsf_mch_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artif_identity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artif_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `artif_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_add_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bussiness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bussiness_card` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_face` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_back` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_back_copy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_body` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_face` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_face_copy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_expire_time` datetime(0) NULL DEFAULT NULL,
  `identity_start_time` datetime(0) NULL DEFAULT NULL,
  `keeper_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `licence_begin_date` datetime(0) NULL DEFAULT NULL,
  `licence_expire_date` datetime(0) NULL DEFAULT NULL,
  `licence_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_check` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_head` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo3` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo4` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rate_alipay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rate_wx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_keeper` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `signin_typ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_hsf_mch_photo
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_hsf_mch_photo`;
CREATE TABLE `lysj_merchant_hsf_mch_photo`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `bussiness` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bussiness_card` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_face` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_back` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_back_copy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_body` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_face` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity_face_copy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_check` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_head` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo3` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_photo4` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_mer_info
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_mer_info`;
CREATE TABLE `lysj_merchant_mer_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `account_holder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_status` int(11) NULL DEFAULT NULL,
  `bank_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bank_outlet` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_lev_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_lev_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_lev_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_license_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `certificate` int(11) NULL DEFAULT NULL,
  `certificate_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company_web` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cus_service_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_business_time` datetime(0) NULL DEFAULT NULL,
  `end_certificate_time` datetime(0) NULL DEFAULT NULL,
  `end_organization_time` datetime(0) NULL DEFAULT NULL,
  `epresentative_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `license` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mini_program_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mini_program_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `organization_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `organization_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `register_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `representative_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `representative_type` int(11) NULL DEFAULT NULL,
  `sell_check` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `short_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `special_qualification_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_business_time` datetime(0) NULL DEFAULT NULL,
  `start_certificate_time` datetime(0) NULL DEFAULT NULL,
  `start_organization_time` datetime(0) NULL DEFAULT NULL,
  `supplement_photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_mer_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_mer_qr_code`;
CREATE TABLE `lysj_merchant_mer_qr_code`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `escription` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` decimal(10, 2) NULL DEFAULT NULL,
  `qr_code` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_merchant
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_merchant`;
CREATE TABLE `lysj_merchant_merchant`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_prorata` decimal(10, 4) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `business_lev_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_lev_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `business_lev_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_merchant_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_merchant_pay_config`;
CREATE TABLE `lysj_merchant_merchant_pay_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `ali_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_app_private_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_app_public_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_secret` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_mchid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_permission
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_permission`;
CREATE TABLE `lysj_merchant_permission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort_order` int(11) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `visible` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_merchant_permission
-- ----------------------------
INSERT INTO `lysj_merchant_permission` VALUES ('1121381546444603392', '2019-04-25 19:52:50', 1, '门店管理', '2019-05-26 22:50:39', NULL, 'ico_store', 1, '', '/store/views', 4, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121381610256744448', '2019-04-25 19:53:05', 1, '门店列表', '2019-04-25 19:55:27', NULL, '', 2, '1121381546444603392', 'store', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121381770214916096', '2019-04-25 19:53:43', 1, '查看门店列表', '2019-04-26 10:27:28', NULL, '', 3, '1121381610256744448', '/merchant/store/list_rewrite', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387275893227520', '2019-04-25 20:15:36', 1, '添加门店', '2019-04-26 10:27:38', NULL, '', 3, '1121381610256744448', '/merchant/store/save_rewrite', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387341911572480', '2019-04-25 20:15:51', 1, '修改门店', '2019-04-25 20:15:51', NULL, NULL, 3, '1121381610256744448', '/merchant/store/update', NULL, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387494663929856', '2019-04-25 20:16:28', 1, '门店详情', '2019-04-25 20:16:34', NULL, '', 2, '1121381546444603392', 'storeDetail', 2, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387582844977152', '2019-04-25 20:16:49', 1, '查看详情', '2019-04-25 20:16:49', NULL, NULL, 3, '1121387494663929856', '/merchant/store/find_one', NULL, 1, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387670564651008', '2019-04-25 20:17:10', 1, '用户管理', '2019-04-26 10:34:13', NULL, 'ico_user', 1, '', '/user/views', 2, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121387796762869760', '2019-04-25 20:17:40', 1, '用户列表', '2019-04-25 20:17:40', NULL, NULL, 2, '1121387670564651008', 'user', NULL, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121388041395650560', '2019-04-25 20:18:38', 1, '添加用户', '2019-05-04 22:16:07', NULL, '', 3, '1121387796762869760', '/merchant/user/save', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121388135721353216', '2019-04-25 20:19:01', 1, '查询用户 ', '2019-05-04 21:32:00', NULL, '', 3, '1121387796762869760', '/merchant/user/list_rewrite', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121388216704974848', '2019-04-25 20:19:20', 1, '修改用户', '2019-04-25 20:19:20', NULL, NULL, 3, '1121387796762869760', '/merchant/user/update', NULL, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1121388307931086848', '2019-04-25 20:19:42', 1, '用户详情', '2019-04-25 20:19:42', NULL, NULL, 2, '1121387670564651008', 'userDetail', NULL, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1121388425241575424', '2019-04-25 20:20:10', 1, '查看详情', '2019-04-25 20:20:10', NULL, NULL, 3, '1121388307931086848', '/merchant/user/detail', NULL, 1, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1121673705337012224', '2019-04-26 15:13:46', 1, '获取商户对应的门店', '2019-04-26 15:13:46', NULL, NULL, 3, '1121387796762869760', '/merchant/store/find_by_merchant_id', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1126312148306378752', '2019-05-09 10:25:17', 1, '移动支付', '2019-05-26 22:50:35', NULL, 'ico_phone', 1, '', '/pay/views', 3, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1126312397510950912', '2019-05-09 10:26:16', 1, '流水列表', '2019-05-09 10:26:16', NULL, NULL, 2, '1126312148306378752', 'flowList', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1126312584329445376', '2019-05-09 10:27:01', 1, '测试权限', '2019-05-09 10:27:01', NULL, NULL, 3, '1126312397510950912', '/merchant/store/list_rewrite', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1126334214543917056', '2019-05-09 11:52:58', 1, '流水列表（订单列表）', '2019-05-09 11:52:58', NULL, NULL, 2, '1126312148306378752', 'flowOrderList', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1126334285511540736', '2019-05-09 11:53:15', 1, '测试权限', '2019-05-09 11:53:15', NULL, NULL, 3, '1126334214543917056', '/merchant/store/list_rewrite', NULL, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1126372480039141376', '2019-05-09 14:25:01', 1, '流水列表（流水订单详情）', '2019-05-09 14:25:01', NULL, NULL, 2, '1126312148306378752', 'flowOrderDetail', 1, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1126372532694433792', '2019-05-09 14:25:13', 1, '权限测试', '2019-05-09 14:25:13', NULL, NULL, 3, '1126372480039141376', '/merchant/store/list_rewrite', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1126661793347944448', '2019-05-10 09:34:39', 1, '流水概览', '2019-05-10 09:34:39', NULL, NULL, 2, '1126312148306378752', 'flowPreview', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1126692964492525568', '2019-05-10 11:38:30', 1, '流水概览（订单列表）', '2019-05-10 11:38:30', NULL, NULL, 2, '1126312148306378752', 'flowPreOrderList', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1126735190014705664', '2019-05-10 14:26:18', 1, '流水概览（订单详情）', '2019-05-10 14:26:18', NULL, NULL, 2, '1126312148306378752', 'flowPreOrderDetail', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1128127934964580352', '2019-05-14 10:40:34', 1, '会员中心', '2019-05-26 22:50:28', NULL, 'ico_member', 1, '', '/member/views', 2, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1128128104842280960', '2019-05-14 10:41:14', 1, '会员列表', '2019-05-14 10:41:14', NULL, NULL, 2, '1128127934964580352', 'memberList', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1128501405955538944', '2019-05-15 11:24:36', 1, '会员消费记录', '2019-05-15 11:24:36', NULL, NULL, 2, '1128127934964580352', 'purchaseHistory', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1128546637245988864', '2019-05-15 14:24:20', 1, '会员消费记录详情', '2019-05-15 14:24:20', NULL, NULL, 2, '1128127934964580352', 'purchaseDetail', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1128559589189632000', '2019-05-15 15:15:48', 1, '会员详情', '2019-05-15 15:15:48', NULL, NULL, 2, '1128127934964580352', 'memberDetail', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1128562006069231616', '2019-05-15 15:25:25', 1, '积分规则', '2019-05-15 15:25:25', NULL, NULL, 2, '1128127934964580352', 'pointsRule', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1128953032588017664', '2019-05-16 17:19:13', 1, '积分商品详情', '2019-05-16 17:19:13', NULL, NULL, 2, '1128127934964580352', 'pointGoodsDetail', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1129211503518564352', '2019-05-17 10:26:17', 1, '积分商品添加', '2019-05-17 10:26:17', NULL, NULL, 2, '1128127934964580352', 'pointGoodsAdd', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1129274706923503616', '2019-05-17 14:37:26', 1, '会员卡设置', '2019-05-17 14:37:26', NULL, NULL, 2, '1128127934964580352', 'MemCardSetting', NULL, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1129282765716602880', '2019-05-17 15:09:27', 1, '会员列表-查看', '2019-05-17 15:09:27', NULL, NULL, 3, '1128128104842280960', '/member/member/list', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1129282841998409728', '2019-05-17 15:09:45', 1, '会员列表-编辑', '2019-05-17 15:09:45', NULL, NULL, 3, '1128128104842280960', '/member/member/update', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129283049054420992', '2019-05-17 15:10:35', 1, '会员详情', '2019-05-17 15:10:35', NULL, NULL, 3, '1128559589189632000', '/member/member/member_detail', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129283176057946112', '2019-05-17 15:11:05', 1, '会员分析-卡片', '2019-05-17 15:12:07', NULL, '', 3, '1128128104842280960', '/member/statistics/member_analyse_card', 1, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129283365963448320', '2019-05-17 15:11:50', 1, '会员分析-时间曲线图', '2019-05-17 15:11:50', NULL, NULL, 3, '1128128104842280960', '/member/statistics/member_analyse_chart_by_time', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129283577259900928', '2019-05-17 15:12:41', 1, '会员分析-渠道饼图', '2019-05-17 15:12:41', NULL, NULL, 3, '1128128104842280960', '/member/statistics/member_analyse_chart_by_channel', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129283703739138048', '2019-05-17 15:13:11', 1, '会员分析-性别分布图', '2019-05-17 15:13:11', NULL, NULL, 3, '1128128104842280960', '/member/statistics/member_analyse_chart_by_sex', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1129284298894098432', '2019-05-17 15:15:33', 1, '储值规则', '2019-05-17 15:15:33', NULL, NULL, 2, '1128127934964580352', 'storedValueRule', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1130662857554411520', '2019-05-21 10:33:27', 1, '会员导入', '2019-05-21 10:33:27', NULL, NULL, 3, '1128128104842280960', '/member/member/member_import', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1130679168820985856', '2019-05-21 11:38:16', 1, '卡券营销', '2019-05-21 11:38:16', NULL, NULL, 2, '1128127934964580352', 'couponMarketing', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1130747234590277632', '2019-05-21 16:08:44', 1, '卡券详情', '2019-05-21 16:08:44', NULL, NULL, 2, '1128127934964580352', 'couponDetail', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1130774252337905664', '2019-05-21 17:56:05', 1, '创建卡券', '2019-05-21 17:56:05', NULL, NULL, 2, '1128127934964580352', 'createCoupon', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1131026141734387712', '2019-05-22 10:37:00', 1, '二维码', '2019-05-22 10:37:00', NULL, NULL, 2, '1126312148306378752', 'qrcode', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1131451929122193408', '2019-05-23 14:48:56', 1, 'App', '2019-05-27 22:19:36', NULL, '', 1, '', 'app', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1131452449945698304', '2019-05-23 14:51:00', 1, '会员管理', '2019-05-23 14:51:00', NULL, NULL, 2, '1131451929122193408', 'member', NULL, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1131452654455767040', '2019-05-23 14:51:49', 1, '会员列表', '2019-05-23 14:51:49', NULL, NULL, 3, '1131452449945698304', '/member/member/app/find_member_list', NULL, 1, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1131456611223748608', '2019-05-23 15:07:32', 1, '积分商城', '2019-05-23 15:07:32', NULL, NULL, 2, '1131451929122193408', '/member/exchange_product/app', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1131457322225385472', '2019-05-23 15:10:22', 1, '用户登录', '2019-05-23 15:11:04', NULL, '', 3, '1131456611223748608', '/member/member/app/member_phone', 1, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1131457666284142592', '2019-05-23 15:11:44', 1, '兑换商品', '2019-05-23 15:11:44', NULL, NULL, 3, '1131456611223748608', '/member/exchange_product/app/exchange', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132659159406362624', '2019-05-26 22:46:02', 1, '首页', '2019-05-26 22:46:02', NULL, 'ico_business', 1, '', '/index', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1132659238410272768', '2019-05-26 22:46:21', 1, '首页', '2019-05-26 22:46:21', NULL, NULL, 2, '1132659159406362624', 'index', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1132659883070603264', '2019-05-26 22:48:55', 1, '订单统计', '2019-05-26 22:48:55', NULL, NULL, 3, '1132659238410272768', '/order/count/order_count', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132659958014427136', '2019-05-26 22:49:12', 1, '订单统计图表', '2019-05-26 22:49:12', NULL, NULL, 3, '1132659238410272768', '/order/count/order_count_line', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132947352202657792', '2019-05-27 17:51:12', 1, '用户账户', '2019-05-27 20:30:22', NULL, '', 2, '1131451929122193408', 'account', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1132987582234378240', '2019-05-27 20:31:04', 1, '修改密码', '2019-05-27 20:31:04', NULL, NULL, 3, '1132947352202657792', '/merchant/management/app/edit_password', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1132987690048962560', '2019-05-27 20:31:30', 1, '账户信息', '2019-05-27 20:31:30', NULL, NULL, 3, '1132947352202657792', '/merchant/management/account_info', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132987863785422848', '2019-05-27 20:32:11', 1, '订单管理', '2019-05-27 20:32:11', NULL, NULL, 2, '1131451929122193408', 'order', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132987984514269184', '2019-05-27 20:32:40', 1, '付款码支付', '2019-05-27 20:32:40', NULL, NULL, 3, '1132987863785422848', '/order/app/scan_pay', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988079796273152', '2019-05-27 20:33:03', 1, '网页支付', '2019-05-27 20:33:03', NULL, NULL, 3, '1132987863785422848', '/order/app/web_pay', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988145189666816', '2019-05-27 20:33:18', 1, '查询订单', '2019-05-27 20:33:18', NULL, NULL, 3, '1132987863785422848', '/order/app/query_order', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988205654753280', '2019-05-27 20:33:33', 1, '退款', '2019-05-27 20:33:33', NULL, NULL, 3, '1132987863785422848', '/order/app/refund', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988270603550720', '2019-05-27 20:33:48', 1, '订单详情', '2019-05-27 20:33:48', NULL, NULL, 3, '1132987863785422848', '/order/app/order_detail', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988339163643904', '2019-05-27 20:34:05', 1, '微信授权登录', '2019-05-27 20:34:05', NULL, NULL, 3, '1132987863785422848', '/order/app/wx_login', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1132988444201598976', '2019-05-27 20:34:30', 1, '修改订单备注', '2019-05-27 20:34:30', NULL, NULL, 3, '1132987863785422848', '/order/app/edit_remarks', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133000647139016704', '2019-05-27 21:22:59', 1, '收银台', '2019-05-27 21:24:35', NULL, '', 1, '', 'pc', 6, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1133000975913730048', '2019-05-27 21:24:17', 1, '账户信息', '2019-05-27 21:24:31', NULL, '', 2, '1133000647139016704', 'account', 1, 0, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001173297676288', '2019-05-27 21:25:05', 1, '账户信息', '2019-05-27 21:25:05', NULL, NULL, 3, '1133000975913730048', '/merchant/management/pc/account_info', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001263278080000', '2019-05-27 21:25:26', 1, '修改密码', '2019-05-27 21:25:26', NULL, NULL, 3, '1133000975913730048', '/merchant/management/pc/edit_password', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001322380017664', '2019-05-27 21:25:40', 1, '订单管理', '2019-05-27 21:25:40', NULL, NULL, 2, '1133000647139016704', 'order', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001502881890304', '2019-05-27 21:26:23', 1, '付款码支付', '2019-05-27 21:26:23', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/scan_pay', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001576303181824', '2019-05-27 21:26:41', 1, '查询订单', '2019-05-27 21:26:41', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/query_order', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001667273441280', '2019-05-27 21:27:02', 1, '订单详情', '2019-05-27 21:27:02', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/order_detail', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001737561587712', '2019-05-27 21:27:19', 1, '退款', '2019-05-27 21:27:19', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/refund', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001902796193792', '2019-05-27 21:27:58', 1, '订单统计', '2019-05-27 21:27:58', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/count_order', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133001985394622464', '2019-05-27 21:28:18', 1, '订单备注', '2019-05-27 21:28:18', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/edit_remarks', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133002067821084672', '2019-05-27 21:28:38', 1, '订单流水', '2019-05-27 21:28:38', NULL, NULL, 3, '1133001322380017664', '/order/management/pc/order_flow', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133002220598607872', '2019-05-27 21:29:14', 1, '员工列表', '2019-05-27 21:29:14', NULL, NULL, 3, '1133000975913730048', '/management/pc/merchant_user', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133015580731916288', '2019-05-27 22:22:20', 1, '门店管理', '2019-05-27 22:22:20', NULL, NULL, 2, '1131451929122193408', 'store', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133015799947214848', '2019-05-27 22:23:12', 1, '获取门店列表selectItem', '2019-05-27 22:23:12', NULL, NULL, 3, '1133015580731916288', '/merchant/store/app/find_by_merchant_id', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133015991987617792', '2019-05-27 22:23:58', 1, '门店列表', '2019-05-27 22:23:58', NULL, NULL, 3, '1133015580731916288', '/merchant/store/app/list', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016071054442496', '2019-05-27 22:24:16', 1, '新增门店', '2019-05-27 22:24:16', NULL, NULL, 3, '1133015580731916288', '/merchant/store/app/save', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016145318789120', '2019-05-27 22:24:34', 1, '修改门店', '2019-05-27 22:24:34', NULL, NULL, 3, '1133015580731916288', '/merchant/store/app/update', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016271366012928', '2019-05-27 22:25:04', 1, '查看门店详情', '2019-05-27 22:25:04', NULL, NULL, 3, '1133015580731916288', '/merchant/store/app/find_one', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016418791604224', '2019-05-27 22:25:39', 1, '员工管理', '2019-05-27 22:25:39', NULL, NULL, 2, '1131451929122193408', 'user', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016521552052224', '2019-05-27 22:26:04', 1, '员工列表', '2019-05-27 22:26:04', NULL, NULL, 3, '1133016418791604224', '/merchant/user/app/list', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016628523581440', '2019-05-27 22:26:29', 1, '新增员工', '2019-05-27 22:26:29', NULL, NULL, 3, '1133016418791604224', '/merchant/user/app/save', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016704725696512', '2019-05-27 22:26:47', 1, '修改员工', '2019-05-27 22:26:47', NULL, NULL, 3, '1133016418791604224', '/merchant/user/app/update', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016803816128512', '2019-05-27 22:27:11', 1, '员工详情', '2019-05-27 22:27:11', NULL, NULL, 3, '1133016418791604224', '/merchant/user/app/detail', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133016864033751040', '2019-05-27 22:27:25', 1, '二维码', '2019-05-27 22:27:25', NULL, NULL, 2, '1131451929122193408', 'qrcode', NULL, 0, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017013774598144', '2019-05-27 22:28:01', 1, '获取员工列表selectItem', '2019-05-27 22:28:01', NULL, NULL, 3, '1133016864033751040', '/merchant/qrcode/app/user/select_item/find_by_user_id', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017103838887936', '2019-05-27 22:28:23', 1, '获取二维码列表', '2019-05-27 22:28:23', NULL, NULL, 3, '1133016864033751040', '/merchant/qrcode/app/list', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017194666541056', '2019-05-27 22:28:44', 1, '添加二维码', '2019-05-27 22:28:44', NULL, NULL, 3, '1133016864033751040', '/merchant/store/app/save', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017309183623168', '2019-05-27 22:29:12', 1, '修改二维码', '2019-05-27 22:29:12', NULL, NULL, 3, '1133016864033751040', '/merchant/store/app/update', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017421687439360', '2019-05-27 22:29:38', 1, '获取二维码详情', '2019-05-27 22:29:38', NULL, NULL, 3, '1133016864033751040', '/merchant/store/qrcode/detail', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1133017500980756480', '2019-05-27 22:29:57', 1, '扫描空二维码', '2019-05-27 22:29:57', NULL, NULL, 3, '1133016864033751040', '/merchant/store/qrcode/scan_blank_qrcode', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1134307664151592960', '2019-05-31 11:56:36', 1, '支付后营销', '2019-05-31 11:56:36', NULL, NULL, 2, '1128127934964580352', 'postPaymentMarketing', 1, 0, 2);
INSERT INTO `lysj_merchant_permission` VALUES ('1147485374516137984', '2019-07-06 20:40:07', 1, '数据概览', '2019-07-06 20:40:07', NULL, NULL, 3, '1132659238410272768', '/auth/running_account_new/date_view', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1147485444103835648', '2019-07-06 20:40:24', 1, '交易数据', '2019-07-06 20:40:24', NULL, NULL, 3, '1132659238410272768', '/auth/running_account_new/date_transaction', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1147485513465040896', '2019-07-06 20:40:40', 1, '左侧图表', '2019-07-06 20:40:40', NULL, NULL, 3, '1132659238410272768', '/auth/running_account_new/data_statistics_left', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1147485585875505152', '2019-07-06 20:40:58', 1, '右侧图表', '2019-07-06 20:40:58', NULL, NULL, 3, '1132659238410272768', '/auth/running_account_new/data_statistics_right', NULL, 1, NULL);
INSERT INTO `lysj_merchant_permission` VALUES ('1148840551008935936', '2019-07-10 14:25:06', 1, 'show_photo', '2019-07-10 14:25:06', NULL, NULL, 3, '1121381610256744448', '/merchant/merchant/show_photo', 1, 1, 1);
INSERT INTO `lysj_merchant_permission` VALUES ('1149595666350485504', '2019-07-12 16:25:40', 1, 'member_list', '2019-07-12 16:25:40', NULL, NULL, 3, '1132659238410272768', '/member/member/member_list', 1, 1, 1);

-- ----------------------------
-- Table structure for lysj_merchant_qr_code_record
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_qr_code_record`;
CREATE TABLE `lysj_merchant_qr_code_record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `download_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `generate_number` int(11) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_merchant_qr_code_record
-- ----------------------------
INSERT INTO `lysj_merchant_qr_code_record` VALUES ('1133276345414074368', '2019-05-28 15:38:30', 1, NULL, '2019-07-03 14:21:27', '服务商', 'C:/temp/lypay/blankcode/20190528/1559029110316.zip', 100, '1120253834480979968');
INSERT INTO `lysj_merchant_qr_code_record` VALUES ('1136272935833714688', '2019-06-05 22:05:53', -1, NULL, '2019-07-03 14:21:27', '服务商', 'C:/temp/lypay/blankcode/20190605/1559743553420.zip', 10, '1120253834480979968');
INSERT INTO `lysj_merchant_qr_code_record` VALUES ('1136471661076430848', '2019-06-06 11:15:33', 1, NULL, '2019-07-03 14:21:27', '服务商', 'C:/temp/lypay/blankcode/20190606/1559790933060.zip', 10, '1120253834480979968');
INSERT INTO `lysj_merchant_qr_code_record` VALUES ('1143430028135120896', '2019-06-25 16:05:37', 1, NULL, '2019-07-03 14:21:27', '服务商', 'C:/temp/lypay/blankcode/20190625/1561449937175.zip', 5, '1120253834480979968');

-- ----------------------------
-- Table structure for lysj_merchant_role
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_role`;
CREATE TABLE `lysj_merchant_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `default_role` bit(1) NULL DEFAULT NULL,
  `role_type` int(11) NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_merchant_role
-- ----------------------------
INSERT INTO `lysj_merchant_role` VALUES ('1', '2019-04-25 20:22:22', 1, '商户', '2019-04-25 20:22:22', b'0', 1, NULL);
INSERT INTO `lysj_merchant_role` VALUES ('2', '2019-04-25 20:22:29', 1, '店长', '2019-04-25 20:22:29', b'0', 2, NULL);
INSERT INTO `lysj_merchant_role` VALUES ('3', '2019-04-26 15:57:04', 1, '店员', '2019-04-26 15:57:23', NULL, 3, NULL);

-- ----------------------------
-- Table structure for lysj_merchant_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_role_permission`;
CREATE TABLE `lysj_merchant_role_permission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `permission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_merchant_role_permission
-- ----------------------------
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240491175936', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121381546444603392', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240520536064', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121381610256744448', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240528924672', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387341911572480', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240549896192', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121381770214916096', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240558284800', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387275893227520', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240566673408', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387494663929856', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240583450624', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387582844977152', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240600227840', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387670564651008', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240612810752', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121387796762869760', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240621199360', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121388041395650560', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240629587968', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121388135721353216', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240650559488', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121388216704974848', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240658948096', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121388307931086848', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1121602240671531008', '2019-04-26 10:29:47', 1, NULL, '2019-04-26 10:29:47', '1121388425241575424', '1121388982341615616');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515882459136', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001902796193792', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515907624960', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129283577259900928', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515911819264', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988205654753280', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515916013568', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121388307931086848', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515920207872', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001985394622464', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515924402176', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129283703739138048', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515928596480', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387582844977152', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515932790784', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988270603550720', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515936985088', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988339163643904', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515941179392', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121388425241575424', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515945373696', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133002067821084672', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515949568000', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1131452449945698304', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515953762304', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988444201598976', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515957956608', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1128559589189632000', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515962150912', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1131452654455767040', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515966345216', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001322380017664', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515970539520', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129283049054420992', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515974733824', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001173297676288', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515978928128', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132987863785422848', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515983122432', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387796762869760', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515987316736', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001502881890304', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515991511040', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001576303181824', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515995705344', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132987690048962560', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742515999899648', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387341911572480', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516004093952', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001263278080000', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516008288256', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132987984514269184', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516016676864', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121388216704974848', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516020871168', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001667273441280', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516025065472', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129282841998409728', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516029259776', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133002220598607872', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516033454080', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988079796273152', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516037648384', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133001737561587712', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516041842688', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129283365963448320', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516046036992', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132988145189666816', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516050231296', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121673705337012224', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516058619904', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129282765716602880', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516062814208', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133000975913730048', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516067008512', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1129283176057946112', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516071202816', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132947352202657792', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516075397120', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121381610256744448', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516079591424', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1130662857554411520', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516083785728', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1128128104842280960', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516087980032', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1132987582234378240', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516092174336', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121381770214916096', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516096368640', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121388041395650560', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516100562944', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1131451929122193408', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516104757248', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387275893227520', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516108951552', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121388135721353216', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516113145856', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1128127934964580352', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516121534464', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387494663929856', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516125728768', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121387670564651008', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516129923072', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1121381546444603392', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1155742516134117376', '2019-07-29 15:31:03', 1, NULL, '2019-07-29 15:31:03', '1133000647139016704', '1');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795192004608', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988205654753280', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795200393216', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017013774598144', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795204587520', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001322380017664', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795208781824', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016145318789120', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795212976128', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016271366012928', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795217170432', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988270603550720', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795221364736', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001173297676288', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795229753344', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017103838887936', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795233947648', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121387796762869760', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795238141952', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001502881890304', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795242336256', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001576303181824', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795246530560', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016418791604224', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795250724864', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132987690048962560', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795254919168', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988339163643904', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795267502080', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001263278080000', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795271696384', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017194666541056', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795275890688', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121388216704974848', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795288473600', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131452449945698304', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795296862208', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131452654455767040', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795301056512', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001667273441280', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795305250816', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016521552052224', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795309445120', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988444201598976', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795313639424', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133002220598607872', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795317833728', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017309183623168', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795322028032', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132987863785422848', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795326222336', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001737561587712', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795330416640', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016628523581440', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795338805248', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133015580731916288', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795342999552', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017421687439360', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795347193856', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126334285511540736', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795351388160', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132987984514269184', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795355582464', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001902796193792', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795359776768', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016704725696512', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795363971072', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133015799947214848', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795368165376', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133017500980756480', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795372359680', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121388307931086848', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795376553984', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988079796273152', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795380748288', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133001985394622464', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795384942592', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016803816128512', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795389136896', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133015991987617792', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795393331200', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121388425241575424', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795397525504', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126372532694433792', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795401719808', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132988145189666816', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795405914112', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133002067821084672', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795410108416', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016864033751040', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795414302720', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133016071054442496', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795418497024', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131457666284142592', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795426885632', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131457322225385472', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795426885633', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133000975913730048', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795431079936', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132947352202657792', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795435274240', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131451929122193408', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795439468544', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126312397510950912', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795443662848', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132987582234378240', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795447857152', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121388041395650560', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795452051456', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126312584329445376', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795456245760', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126334214543917056', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795464634368', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121388135721353216', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795468828672', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121673705337012224', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795473022976', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126372480039141376', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795477217280', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131456611223748608', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795481411584', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1121387670564651008', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795489800192', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126312148306378752', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795493994496', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1133000647139016704', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795510771712', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1131026141734387712', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795514966016', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126735190014705664', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795519160320', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126692964492525568', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795523354624', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1126661793347944448', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795527548928', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128127934964580352', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795531743232', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128559589189632000', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795535937536', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129283049054420992', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795540131840', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128128104842280960', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795544326144', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129282841998409728', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795548520448', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129283365963448320', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795552714752', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129283577259900928', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795561103360', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129283703739138048', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795565297664', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129282765716602880', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795569491968', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129283176057946112', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795573686272', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1130662857554411520', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795577880576', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128501405955538944', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795594657792', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128546637245988864', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795607240704', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128562006069231616', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795611435008', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1128953032588017664', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795619823616', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129211503518564352', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795649183744', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129284298894098432', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795653378048', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1130679168820985856', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795657572352', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1130747234590277632', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795665960960', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1134307664151592960', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795670155264', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1130774252337905664', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795691126784', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1129274706923503616', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795699515392', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132659238410272768', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795703709696', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132659883070603264', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795707904000', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132659958014427136', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795712098304', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1147485444103835648', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795716292608', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1147485513465040896', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795720486912', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1147485585875505152', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795724681216', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1147485374516137984', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795728875520', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1149595666350485504', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159007795737264128', '2019-08-07 15:46:06', 1, NULL, '2019-08-07 15:46:06', '1132659159406362624', '2');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589341044736', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017421687439360', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589345239040', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133002220598607872', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589353627648', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132987863785422848', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589357821952', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126372532694433792', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589362016256', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016628523581440', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589370404864', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001667273441280', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589374599168', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1130774252337905664', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589382987776', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133015580731916288', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589391376384', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017500980756480', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589395570688', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132987984514269184', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589403959296', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016704725696512', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589408153600', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001737561587712', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589416542208', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133015799947214848', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589420736512', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133015991987617792', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589429125120', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121388307931086848', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589445902336', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988079796273152', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589458485248', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016803816128512', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589462679552', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001902796193792', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589466873856', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132659883070603264', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589475262464', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129282841998409728', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589492039680', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016071054442496', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589496233984', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387582844977152', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589504622592', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131457666284142592', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589513011200', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121388425241575424', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589525594112', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988145189666816', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589529788416', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016864033751040', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589538177024', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001985394622464', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589542371328', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132659958014427136', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589550759936', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129283365963448320', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589571731456', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129283577259900928', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589580120064', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016145318789120', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589584314368', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988205654753280', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589592702976', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017013774598144', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589605285888', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133002067821084672', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589613674496', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129283703739138048', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589634646016', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016271366012928', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589651423232', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001322380017664', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589655617536', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128559589189632000', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589659811840', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988270603550720', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589668200448', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017103838887936', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589886304256', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387796762869760', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589898887168', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121388216704974848', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589903081472', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131452449945698304', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589919858688', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126334285511540736', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589928247296', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016418791604224', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589936635904', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001502881890304', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589940830208', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132987690048962560', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589945024512', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129283049054420992', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589953413120', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988339163643904', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589961801728', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017194666541056', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589970190336', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001173297676288', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589978578944', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001263278080000', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008589995356160', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131452654455767040', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590012133376', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133016521552052224', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590045687808', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133001576303181824', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590049882112', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129274706923503616', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590062465024', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132988444201598976', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590070853632', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387341911572480', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590075047936', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133017309183623168', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590079242240', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121388135721353216', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590083436544', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1130662857554411520', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590091825152', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132659159406362624', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590096019456', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1130747234590277632', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590104408064', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121381770214916096', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590108602368', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387275893227520', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590112796672', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121673705337012224', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590121185280', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128501405955538944', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590125379584', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132659238410272768', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590129573888', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128128104842280960', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590137962496', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1134307664151592960', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590142156800', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131456611223748608', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590146351104', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128546637245988864', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590154739712', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126312397510950912', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590167322624', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128562006069231616', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590175711232', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126312584329445376', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590179905536', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131457322225385472', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590188294144', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128953032588017664', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590192488448', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131451929122193408', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590196682752', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126334214543917056', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590209265664', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132947352202657792', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590217654272', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129211503518564352', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590221848576', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133000975913730048', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590230237184', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129282765716602880', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590234431488', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129284298894098432', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590238625792', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121381610256744448', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590255403008', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121388041395650560', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590259597312', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1129283176057946112', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590263791616', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126372480039141376', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590272180224', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1132987582234378240', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590276374528', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1130679168820985856', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590280568832', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387494663929856', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590284763136', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1128127934964580352', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590301540352', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121387670564651008', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590322511872', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126312148306378752', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590326706176', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1121381546444603392', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590330900480', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1133000647139016704', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590347677696', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1131026141734387712', '3');
INSERT INTO `lysj_merchant_role_permission` VALUES ('1159008590356066304', '2019-08-07 15:49:16', 1, NULL, '2019-08-07 15:49:16', '1126692964492525568', '3');

-- ----------------------------
-- Table structure for lysj_merchant_sn_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_sn_config`;
CREATE TABLE `lysj_merchant_sn_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `flag` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_store
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_store`;
CREATE TABLE `lysj_merchant_store`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payment_desciption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phonto_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `store_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_flag` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_user
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_user`;
CREATE TABLE `lysj_merchant_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_type` int(11) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `photo_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_merchant_user_role
-- ----------------------------
DROP TABLE IF EXISTS `lysj_merchant_user_role`;
CREATE TABLE `lysj_merchant_user_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_merchant_user_role
-- ----------------------------
INSERT INTO `lysj_merchant_user_role` VALUES ('1121693365688221696', '2019-04-26 16:31:53', 1, NULL, '2019-04-26 16:31:53', '2', '1121693365574975488');
INSERT INTO `lysj_merchant_user_role` VALUES ('1121694252145983488', '2019-04-26 16:35:24', 1, NULL, '2019-04-26 16:35:24', '2', '1121694252133400576');
INSERT INTO `lysj_merchant_user_role` VALUES ('1121694912522371072', '2019-04-26 16:38:02', 1, NULL, '2019-04-26 16:38:02', '2', '1121694879227985920');
INSERT INTO `lysj_merchant_user_role` VALUES ('1121699603880755200', '2019-04-26 16:56:40', 1, NULL, '2019-04-26 16:56:40', '2', '1121694509474922496');
INSERT INTO `lysj_merchant_user_role` VALUES ('1121712510903402496', '2019-04-26 17:47:58', 1, NULL, '2019-04-26 17:47:58', '2', '1121712510785961984');
INSERT INTO `lysj_merchant_user_role` VALUES ('1124601173337325568', '2019-05-04 17:06:28', 1, NULL, '2019-05-04 17:06:28', '2', '1123608652746473472');
INSERT INTO `lysj_merchant_user_role` VALUES ('1124601577626288128', '2019-05-04 17:08:05', 1, NULL, '2019-05-04 17:08:05', '1', '1121339707578531840');
INSERT INTO `lysj_merchant_user_role` VALUES ('1124641883654602752', '2019-05-04 19:48:15', 1, NULL, '2019-05-04 19:48:15', '2', '1124641883583299584');
INSERT INTO `lysj_merchant_user_role` VALUES ('1124671879655866368', '2019-05-04 21:47:26', 1, NULL, '2019-05-04 21:47:26', '2', '1124671879542620160');
INSERT INTO `lysj_merchant_user_role` VALUES ('1124960089460064256', '2019-05-05 16:52:41', 1, NULL, '2019-05-05 16:52:41', '2', '1124960084963770368');
INSERT INTO `lysj_merchant_user_role` VALUES ('1125389400621072384', '2019-05-06 21:18:36', 1, NULL, '2019-05-06 21:18:36', '2', '1125389400532992000');
INSERT INTO `lysj_merchant_user_role` VALUES ('1131159893718482944', '2019-05-22 19:28:29', 1, NULL, '2019-05-22 19:28:29', '2', '1128294007965892608');
INSERT INTO `lysj_merchant_user_role` VALUES ('1131165877027356672', '2019-05-22 19:52:16', 1, NULL, '2019-05-22 19:52:16', '3', '1131165877018968064');
INSERT INTO `lysj_merchant_user_role` VALUES ('1132990320355721216', '2019-05-27 20:41:56', 1, NULL, '2019-05-27 20:41:56', '3', '1132990320225697792');
INSERT INTO `lysj_merchant_user_role` VALUES ('1133654053172166656', '2019-05-29 16:39:23', 1, NULL, '2019-05-29 16:39:23', '2', '1133654052320722944');
INSERT INTO `lysj_merchant_user_role` VALUES ('1138384608438665216', '2019-06-11 17:56:55', 1, NULL, '2019-06-11 17:56:55', '2', '1138384608329613312');
INSERT INTO `lysj_merchant_user_role` VALUES ('1144499025530208256', '2019-06-28 14:53:26', 1, NULL, '2019-06-28 14:53:26', '2', '1144499025421156352');
INSERT INTO `lysj_merchant_user_role` VALUES ('1145244884926681088', NULL, 1, NULL, NULL, '2', '1145244884909903872');
INSERT INTO `lysj_merchant_user_role` VALUES ('1145270552362336256', '2019-06-30 17:59:12', 1, NULL, '2019-06-30 17:59:12', '3', '1140640925832331264');
INSERT INTO `lysj_merchant_user_role` VALUES ('1145273884247838720', '2019-06-30 18:12:27', 1, NULL, '2019-06-30 18:12:27', '2', '1145272601151524864');

-- ----------------------------
-- Table structure for lysj_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `lysj_operation_log`;
CREATE TABLE `lysj_operation_log`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `cost_time` int(11) NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_param` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token_load` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lysj_operation_log
-- ----------------------------
INSERT INTO `lysj_operation_log` VALUES ('1155801761221230592', '2019-07-29 19:26:28', 1, '查看角色', '2019-07-29 19:26:28', 224, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155802946187608064', '2019-07-29 19:31:11', 1, '查看角色', '2019-07-29 19:31:11', 71, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155803006958878720', '2019-07-29 19:31:25', 1, '查看角色', '2019-07-29 19:31:25', 49, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155803062109782016', '2019-07-29 19:31:38', 1, '查看角色', '2019-07-29 19:31:38', 43, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155803121832476672', '2019-07-29 19:31:53', 1, '查看角色', '2019-07-29 19:31:53', 38, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155804502668656640', '2019-07-29 19:37:22', 1, '分配角色权限', '2019-07-29 19:37:22', 114, '121.204.59.56', '{\"permIds\":[\"93077261917360128\",\"93079494650236928\",\"1121379155653562368\",\"1121379246753845248\",\"93077383510233088\",\"1121379476878528512\",\"93083145556987904\",\"1145677864296222720\",\"1121379329096421376\",\"93077427508482048\",\"1121379545874829312\",\"94620316218691584\",\"1145677912274866176\",\"1145875809642950656\",\"1121379395525808128\",\"93078534242701312\",\"93077521351839744\",\"94620846584238080\",\"1145875901003280384\",\"93079281722200064\",\"93077585952509952\",\"94620922836684800\",\"94621272096378880\",\"93077637643112448\",\"94660438846869504\",\"93079415147204608\",\"93077720203792384\",\"1126048739157479424\",\"93079458038157312\",\"1121378876778483712\",\"1128850623320690688\",\"1126048657469214720\",\"1145612467224154112\",\"1126302195977949184\",\"93076167321456640\",\"1121379599054409728\",\"1121379636304023552\",\"1145677817018028032\",\"1121379672974823424\",\"1145678566724702208\",\"1121378693676142592\",\"1128850752098406400\",\"93077149195440128\",\"1145875700498771968\",\"1145612342410055680\",\"1121377871777107968\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1147485829380018176\"],\"id\":[\"1125379513551818752\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155804504870666240', '2019-07-29 19:37:22', 1, '查看角色', '2019-07-29 19:37:22', 36, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155805464892325888', '2019-07-29 19:41:11', 1, '分配角色权限', '2019-07-29 19:41:11', 154, '121.204.59.56', '{\"permIds\":[\"1120885301695352832\",\"1125335829271678976\",\"1120138502072307712\",\"1122415447657025536\",\"1138434532731654144\",\"1121021432852254720\",\"1120885408629133312\",\"1125335936314511360\",\"1120138922018607104\",\"1121020400751480832\",\"1122415553840025600\",\"1138434613698498560\",\"1120890590393929728\",\"1125336054652604416\",\"1120138665130070016\",\"1122416024562569216\",\"1138434712235282432\",\"1120249839297368064\",\"1132695442828693504\",\"1125336283560939520\",\"1120139014914052096\",\"1121020487791677440\",\"1125686227446665216\",\"1120215613533458432\",\"1144515994153259008\",\"1121020556418879488\",\"1121322967737962496\",\"1125335526723948544\",\"1125335634244931584\",\"1144516070258905088\",\"1126048739157479424\",\"1121021298873602048\",\"1125335754852143104\",\"1136262106396626944\",\"1138424763031621632\",\"1121021362404724736\",\"1128850623320690688\",\"1126302195977949184\",\"1118719203902361600\",\"1126039278061752320\",\"1120976795471056896\",\"1121020255246880768\",\"1118775503004766208\",\"1126055950957023232\",\"1120984354198261760\",\"1121021006455115776\",\"1121021220918267904\",\"1123055053884755968\",\"1135801397296631808\",\"1120138324154126336\",\"1121021132301012992\",\"1136262002197532672\",\"1122415637793214464\",\"1138434390536359936\",\"1135801629900148736\",\"1120139058983604224\",\"1120885208325951488\",\"1123120569223614464\",\"1121021192510246912\",\"1121322868307791872\",\"1122786743125413888\",\"1144515850582233088\",\"1120249707625582592\",\"1128850752098406400\",\"1118710330831278080\",\"1122405413829087232\",\"1132705329000353792\",\"1122788129565184000\",\"1120215715127889920\",\"1120652796773928960\",\"1133646405127245824\",\"1132695364579758080\",\"1138423767249326080\",\"1125686023515410432\",\"1120935870153756672\",\"1120883550187556864\",\"1120659294870704128\",\"1126048657469214720\",\"1122415158950498304\",\"1126039093982138368\",\"1120935960939466752\",\"1118718810061410304\",\"1120138083522711552\",\"1121019972366241792\",\"1118702551437545472\",\"1120883336915587072\",\"1122786673026011136\",\"1147485883532677120\",\"1147485969557852160\",\"1147485829380018176\",\"1147486037375553536\",\"1150957440698691584\",\"1150957853829246976\",\"1151015556421267456\",\"1151015638671568896\",\"1151015948492222464\",\"1151016030406979584\",\"1151183290868977664\",\"1151183403611869184\",\"1151014706185515008\",\"1151015357279907840\",\"1151183156131155968\",\"1149149804109295616\"],\"id\":[\"2\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155805466674905088', '2019-07-29 19:41:12', 1, '查看角色', '2019-07-29 19:41:12', 23, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155805934734065664', '2019-07-29 19:43:03', 1, '分配角色权限', '2019-07-29 19:43:03', 134, '121.204.59.56', '{\"permIds\":[\"1121328315710443520\",\"1126048739157479424\",\"1121038541992890368\",\"1125335634244931584\",\"1121038203181207552\",\"1118719269438361600\",\"1121038589027815424\",\"1125335754852143104\",\"1121038246659362816\",\"1120885301695352832\",\"1121326915307835392\",\"1121251046644125696\",\"1122415447657025536\",\"1125335829271678976\",\"1125335936314511360\",\"1121038288224915456\",\"1120885408629133312\",\"1121328016371355648\",\"1121251136624529408\",\"1122415553840025600\",\"1125336054652604416\",\"1121038382567395328\",\"1120890590393929728\",\"1121328120390094848\",\"1121251201602686976\",\"1122416024562569216\",\"1125336283560939520\",\"1123110155618226176\",\"1121038426041356288\",\"1132695442828693504\",\"1123055197682274304\",\"1125686227446665216\",\"1123111058995810304\",\"1121038157865947136\",\"1121328238115819520\",\"1123111147197829120\",\"1121038499303264256\",\"1125335526723948544\",\"1128850623320690688\",\"1125686023515410432\",\"1118718912163352576\",\"1132695364579758080\",\"1120883550187556864\",\"1121326820071968768\",\"1126048657469214720\",\"1126039093982138368\",\"1122415158950498304\",\"1126302195977949184\",\"1126039278061752320\",\"1122319136639291392\",\"1126055950957023232\",\"1124973065952956416\",\"1135801397296631808\",\"1118721204585369600\",\"1122415637793214464\",\"1135801629900148736\",\"1118722906751373312\",\"1121249779788156928\",\"1121038340116844544\",\"1120885208325951488\",\"1122786743125413888\",\"1123120569223614464\",\"1121038118305271808\",\"1128850752098406400\",\"1122788129565184000\",\"1122405413829087232\",\"1132705329000353792\",\"1118718810061410304\",\"1121037450744041472\",\"1120883336915587072\",\"1121326643986698240\",\"1122786673026011136\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1147485829380018176\"],\"id\":[\"7\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155805936344678400', '2019-07-29 19:43:04', 1, '查看角色', '2019-07-29 19:43:04', 23, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155806314666704896', '2019-07-29 19:44:34', 1, '分配角色权限', '2019-07-29 19:44:34', 62, '121.204.59.56', '{\"permIds\":[\"1120890590393929728\",\"1121328120390094848\",\"1122416024562569216\",\"1125336054652604416\",\"1123110155618226176\",\"1132695442828693504\",\"1123055197682274304\",\"1125336283560939520\",\"1125686227446665216\",\"1123111058995810304\",\"1121328238115819520\",\"1123111147197829120\",\"1125335526723948544\",\"1121328315710443520\",\"1126048739157479424\",\"1125335634244931584\",\"1118719269438361600\",\"1125335754852143104\",\"1120885301695352832\",\"1121326915307835392\",\"1122415447657025536\",\"1125335829271678976\",\"1120885408629133312\",\"1121328016371355648\",\"1122415553840025600\",\"1125335936314511360\",\"1128850623320690688\",\"1135801397296631808\",\"1118721204585369600\",\"1135801629900148736\",\"1118722906751373312\",\"1122415637793214464\",\"1120885208325951488\",\"1122786743125413888\",\"1123120569223614464\",\"1122788129565184000\",\"1128850752098406400\",\"1122405413829087232\",\"1132705329000353792\",\"1125686023515410432\",\"1118718912163352576\",\"1132695364579758080\",\"1120883550187556864\",\"1121326820071968768\",\"1126039093982138368\",\"1126048657469214720\",\"1122415158950498304\",\"1126039278061752320\",\"1126302195977949184\",\"1122319136639291392\",\"1126055950957023232\",\"1124973065952956416\",\"1118718810061410304\",\"1120883336915587072\",\"1121326643986698240\",\"1122786673026011136\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1147485829380018176\"],\"id\":[\"4\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155806316264734720', '2019-07-29 19:44:34', 1, '查看角色', '2019-07-29 19:44:34', 34, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155806795757568000', '2019-07-29 19:46:29', 1, '分配角色权限', '2019-07-29 19:46:29', 62, '121.204.59.56', '{\"permIds\":[\"1120890590393929728\",\"1125335526723948544\",\"1125335634244931584\",\"1118719269438361600\",\"1125335754852143104\",\"1126048739157479424\",\"1125335829271678976\",\"1125335936314511360\",\"1122415447657025536\",\"1125336054652604416\",\"1123110155618226176\",\"1122415553840025600\",\"1120885301695352832\",\"1125336283560939520\",\"1123111058995810304\",\"1122416024562569216\",\"1120885408629133312\",\"1123111147197829120\",\"1128850623320690688\",\"1118718912163352576\",\"1128850752098406400\",\"1120885208325951488\",\"1122405413829087232\",\"1122319136639291392\",\"1124973065952956416\",\"1126048657469214720\",\"1122415158950498304\",\"1118721204585369600\",\"1126302195977949184\",\"1120883550187556864\",\"1118722906751373312\",\"1122786743125413888\",\"1122788129565184000\",\"1122415637793214464\",\"1118718810061410304\",\"1120883336915587072\",\"1122786673026011136\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1147485829380018176\",\"1150957440698691584\",\"1150957853829246976\",\"1151015556421267456\",\"1151015638671568896\",\"1151015948492222464\",\"1151016030406979584\",\"1151183290868977664\",\"1151183403611869184\",\"1151014706185515008\",\"1151015357279907840\",\"1151183156131155968\",\"1120138083522711552\",\"1120138502072307712\",\"1120138922018607104\",\"1120138665130070016\",\"1120139014914052096\",\"1120215613533458432\",\"1120215715127889920\",\"1120935870153756672\",\"1120935960939466752\",\"1120976795471056896\",\"1120984354198261760\",\"1120138324154126336\",\"1120249839297368064\",\"1120139058983604224\",\"1120249707625582592\",\"1120652796773928960\",\"1120659294870704128\",\"1121019972366241792\",\"1121020400751480832\",\"1121021220918267904\",\"1121020487791677440\",\"1121021192510246912\",\"1121020556418879488\",\"1121021298873602048\",\"1121021362404724736\",\"1121021432852254720\",\"1121020255246880768\",\"1121021006455115776\",\"1121021132301012992\",\"1126039093982138368\",\"1126039278061752320\",\"1126055950957023232\",\"1135801397296631808\",\"1135801629900148736\"],\"id\":[\"6\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155806797158465536', '2019-07-29 19:46:29', 1, '查看角色', '2019-07-29 19:46:29', 16, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155807313607311360', '2019-07-29 19:48:32', 1, '分配角色权限', '2019-07-29 19:48:32', 54, '121.204.59.56', '{\"permIds\":[\"1120885301695352832\",\"1125336283560939520\",\"1123111058995810304\",\"1122416024562569216\",\"1120885408629133312\",\"1123111147197829120\",\"1120890590393929728\",\"1125335526723948544\",\"1125335634244931584\",\"1118719269438361600\",\"1125335754852143104\",\"1125335829271678976\",\"1125335936314511360\",\"1122415447657025536\",\"1125336054652604416\",\"1123110155618226176\",\"1122415553840025600\",\"1122786743125413888\",\"1122788129565184000\",\"1122415637793214464\",\"1118718912163352576\",\"1120885208325951488\",\"1122405413829087232\",\"1122319136639291392\",\"1124973065952956416\",\"1122415158950498304\",\"1118721204585369600\",\"1120883550187556864\",\"1118722906751373312\",\"1118718810061410304\",\"1120883336915587072\",\"1122786673026011136\",\"1128850752098406400\",\"1126048739157479424\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1126048657469214720\",\"1126302195977949184\",\"1147485829380018176\",\"1128850623320690688\",\"1123120569223614464\",\"1132705329000353792\",\"1121037450744041472\",\"1121038203181207552\",\"1121038246659362816\",\"1121038288224915456\",\"1121038382567395328\",\"1121038426041356288\",\"1121038340116844544\",\"1121038499303264256\",\"1121038541992890368\",\"1121038589027815424\",\"1121251046644125696\",\"1121251136624529408\",\"1121251201602686976\",\"1121249779788156928\",\"1121038118305271808\",\"1121038157865947136\",\"1121326643986698240\",\"1121328238115819520\",\"1121328315710443520\",\"1121326820071968768\",\"1121326915307835392\",\"1121328016371355648\",\"1121328120390094848\",\"1123055197682274304\",\"1126039093982138368\",\"1126039278061752320\",\"1126055950957023232\",\"1135801397296631808\",\"1135801629900148736\"],\"id\":[\"10\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155807315180175360', '2019-07-29 19:48:32', 1, '查看角色', '2019-07-29 19:48:32', 17, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155807740813950976', '2019-07-29 19:50:14', 1, '分配角色权限', '2019-07-29 19:50:14', 41, '121.204.59.56', '{\"permIds\":[\"1120885301695352832\",\"1125336283560939520\",\"1123111058995810304\",\"1122416024562569216\",\"1120885408629133312\",\"1123111147197829120\",\"1120890590393929728\",\"1125335526723948544\",\"1125335634244931584\",\"1118719269438361600\",\"1125335754852143104\",\"1126048739157479424\",\"1125335829271678976\",\"1125335936314511360\",\"1122415447657025536\",\"1125336054652604416\",\"1123110155618226176\",\"1122415553840025600\",\"1128850623320690688\",\"1122786743125413888\",\"1122788129565184000\",\"1122415637793214464\",\"1126039093982138368\",\"1118718912163352576\",\"1128850752098406400\",\"1120885208325951488\",\"1126039278061752320\",\"1122405413829087232\",\"1126055950957023232\",\"1122319136639291392\",\"1124973065952956416\",\"1126048657469214720\",\"1122415158950498304\",\"1135801397296631808\",\"1118721204585369600\",\"1126302195977949184\",\"1135801629900148736\",\"1120883550187556864\",\"1118722906751373312\",\"1118718810061410304\",\"1120883336915587072\",\"1122786673026011136\",\"1147485883532677120\",\"1147485969557852160\",\"1147486037375553536\",\"1147485829380018176\",\"1123120569223614464\",\"1132705329000353792\",\"1121326643986698240\",\"1121328238115819520\",\"1121328315710443520\",\"1121326820071968768\",\"1121326915307835392\",\"1121328016371355648\",\"1121328120390094848\",\"1123055197682274304\"],\"id\":[\"9\"]}', 'POST', '/auth/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155807742420369408', '2019-07-29 19:50:14', 1, '查看角色', '2019-07-29 19:50:14', 22, '121.204.59.56', '{\"pageNumber\":[\"1\"],\"pageSize\":[\"10\"]}', 'GET', '/auth/role/list', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155808121782583296', '2019-07-29 19:51:45', 1, '分配角色权限', '2019-07-29 19:51:45', 124, '121.204.59.56', '{\"permIds\":[\"1132987863785422848\",\"1133016628523581440\",\"1133001667273441280\",\"1133015580731916288\",\"1133017421687439360\",\"1133002220598607872\",\"1132987984514269184\",\"1133016704725696512\",\"1133001737561587712\",\"1133015799947214848\",\"1133017500980756480\",\"1121388307931086848\",\"1132988079796273152\",\"1133016803816128512\",\"1133001902796193792\",\"1132659883070603264\",\"1129282841998409728\",\"1133015991987617792\",\"1121387582844977152\",\"1131457666284142592\",\"1121388425241575424\",\"1132988145189666816\",\"1133016864033751040\",\"1133001985394622464\",\"1132659958014427136\",\"1129283365963448320\",\"1133016071054442496\",\"1132988205654753280\",\"1133017013774598144\",\"1133002067821084672\",\"1129283577259900928\",\"1133016145318789120\",\"1133016271366012928\",\"1133001322380017664\",\"1132988270603550720\",\"1133017103838887936\",\"1121387796762869760\",\"1129283703739138048\",\"1133016418791604224\",\"1133001502881890304\",\"1132987690048962560\",\"1132988339163643904\",\"1133017194666541056\",\"1133001173297676288\",\"1121388216704974848\",\"1131452449945698304\",\"1131452654455767040\",\"1133016521552052224\",\"1133001576303181824\",\"1132988444201598976\",\"1121387341911572480\",\"1133017309183623168\",\"1133001263278080000\",\"1130662857554411520\",\"1132659159406362624\",\"1121381770214916096\",\"1121388135721353216\",\"1121673705337012224\",\"1126661793347944448\",\"1132659238410272768\",\"1128128104842280960\",\"1121387275893227520\",\"1126692964492525568\",\"1131456611223748608\",\"1126735190014705664\",\"1131457322225385472\",\"1132947352202657792\",\"1133000975913730048\",\"1131451929122193408\",\"1121381610256744448\",\"1129282765716602880\",\"1129283176057946112\",\"1132987582234378240\",\"1121388041395650560\",\"1121387494663929856\",\"1128127934964580352\",\"1121387670564651008\",\"1126312148306378752\",\"1121381546444603392\",\"1133000647139016704\",\"1147485513465040896\",\"1147485444103835648\",\"1147485374516137984\",\"1147485585875505152\",\"1149595666350485504\",\"1148840551008935936\"],\"id\":[\"3\"]}', 'POST', '/merchant/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155808531314425856', '2019-07-29 19:53:22', 1, '分配角色权限', '2019-07-29 19:53:22', 92, '121.204.59.56', '{\"permIds\":[\"1131452449945698304\",\"1133001576303181824\",\"1133016418791604224\",\"1132987690048962560\",\"1121387341911572480\",\"1132988339163643904\",\"1133001263278080000\",\"1133017194666541056\",\"1121388216704974848\",\"1131452654455767040\",\"1133001667273441280\",\"1133016521552052224\",\"1129282841998409728\",\"1132988444201598976\",\"1133002220598607872\",\"1133017309183623168\",\"1132987863785422848\",\"1133001737561587712\",\"1133016628523581440\",\"1129283365963448320\",\"1133015580731916288\",\"1133017421687439360\",\"1133017500980756480\",\"1132987984514269184\",\"1133001902796193792\",\"1133016704725696512\",\"1129283577259900928\",\"1133015799947214848\",\"1121388307931086848\",\"1132988079796273152\",\"1133001985394622464\",\"1133016803816128512\",\"1129283703739138048\",\"1121387582844977152\",\"1133015991987617792\",\"1133016071054442496\",\"1131457666284142592\",\"1121388425241575424\",\"1132988145189666816\",\"1133002067821084672\",\"1133016864033751040\",\"1133001322380017664\",\"1133016145318789120\",\"1128559589189632000\",\"1132988205654753280\",\"1133017013774598144\",\"1133001502881890304\",\"1133016271366012928\",\"1129283049054420992\",\"1132988270603550720\",\"1133001173297676288\",\"1133017103838887936\",\"1121387796762869760\",\"1128128104842280960\",\"1121388041395650560\",\"1132987582234378240\",\"1121381770214916096\",\"1121388135721353216\",\"1121387275893227520\",\"1121673705337012224\",\"1131456611223748608\",\"1129282765716602880\",\"1131457322225385472\",\"1133000975913730048\",\"1129283176057946112\",\"1131451929122193408\",\"1132947352202657792\",\"1121381610256744448\",\"1130662857554411520\",\"1121387494663929856\",\"1128127934964580352\",\"1121387670564651008\",\"1121381546444603392\",\"1133000647139016704\",\"1132659238410272768\",\"1132659883070603264\",\"1132659958014427136\",\"1147485444103835648\",\"1147485513465040896\",\"1147485585875505152\",\"1147485374516137984\",\"1149595666350485504\",\"1132659159406362624\",\"1126735190014705664\",\"1126312148306378752\",\"1126692964492525568\",\"1126661793347944448\",\"1148840551008935936\"],\"id\":[\"2\"]}', 'POST', '/merchant/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');
INSERT INTO `lysj_operation_log` VALUES ('1155808714718756864', '2019-07-29 19:54:06', 1, '分配角色权限', '2019-07-29 19:54:06', 4, '121.204.59.56', '{\"id\":[\"1\"]}', 'POST', '/merchant/role/edit_role_perm', '{\"sub\":\"93088344895918080\",\"companyId\":\"1120253834480979968\",\"iss\":\"auth\",\"exp\":1565004312}');

-- ----------------------------
-- Table structure for lysj_order_order
-- ----------------------------
DROP TABLE IF EXISTS `lysj_order_order`;
CREATE TABLE `lysj_order_order`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `act_pay_price` decimal(10, 2) NULL DEFAULT NULL,
  `auth_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dis_count_price` decimal(10, 2) NULL DEFAULT NULL,
  `interest_rate` decimal(19, 4) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_channel` int(11) NULL DEFAULT NULL,
  `pay_client` int(11) NULL DEFAULT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `pay_way` int(11) NULL DEFAULT NULL,
  `refund_order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `refund_pay_price` decimal(10, 2) NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  `refund_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transaction_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `out_trade_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `applet_store` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `recharge_flag` int(11) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_ali_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_ali_config`;
CREATE TABLE `lysj_pay_ali_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_auth_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_hsf_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_hsf_config`;
CREATE TABLE `lysj_pay_hsf_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `ali_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_hyb_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_hyb_config`;
CREATE TABLE `lysj_pay_hyb_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  `wx_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_pay_channel`;
CREATE TABLE `lysj_pay_pay_channel`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_way` int(11) NULL DEFAULT NULL,
  `scan_pay_channel` int(11) NULL DEFAULT NULL,
  `web_pay_channel` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_sxf_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_sxf_config`;
CREATE TABLE `lysj_pay_sxf_config`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `ali_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `org_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `wx_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lysj_pay_top_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_top_config`;
CREATE TABLE `lysj_pay_top_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `ali_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_private_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ali_public_key` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_app_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_cert_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_mch_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_tts_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_tts_config`;
CREATE TABLE `lysj_pay_tts_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `city_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mch_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `partner_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `public_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `region_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_wx_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_wx_config`;
CREATE TABLE `lysj_pay_wx_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  `sub_mch_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_pay_yrm_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_pay_yrm_config`;
CREATE TABLE `lysj_pay_yrm_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `ali_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wx_interest_rate` decimal(10, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_utils_log
-- ----------------------------
DROP TABLE IF EXISTS `lysj_utils_log`;
CREATE TABLE `lysj_utils_log`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `cost_time` int(11) NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_param` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token_load` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lysj_wx_open_config
-- ----------------------------
DROP TABLE IF EXISTS `lysj_wx_open_config`;
CREATE TABLE `lysj_wx_open_config`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `service_provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `filecontent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `msg_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for province_info
-- ----------------------------
DROP TABLE IF EXISTS `province_info`;
CREATE TABLE `province_info`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `province_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `province_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of province_info
-- ----------------------------
INSERT INTO `province_info` VALUES ('24', '520000', '贵州省');
INSERT INTO `province_info` VALUES ('14', '360000', '江西省');
INSERT INTO `province_info` VALUES ('1', '110000', '北京市');
INSERT INTO `province_info` VALUES ('23', '510000', '四川省');
INSERT INTO `province_info` VALUES ('8', '230000', '黑龙江省');
INSERT INTO `province_info` VALUES ('2', '120000', '天津市');
INSERT INTO `province_info` VALUES ('9', '310000', '上海市');
INSERT INTO `province_info` VALUES ('6', '210000', '辽宁省');
INSERT INTO `province_info` VALUES ('21', '460000', '海南省');
INSERT INTO `province_info` VALUES ('15', '370000', '山东省');
INSERT INTO `province_info` VALUES ('16', '410000', '河南省');
INSERT INTO `province_info` VALUES ('26', '540000', '西藏自治区');
INSERT INTO `province_info` VALUES ('13', '350000', '福建省');
INSERT INTO `province_info` VALUES ('17', '420000', '湖北省');
INSERT INTO `province_info` VALUES ('10', '320000', '江苏省');
INSERT INTO `province_info` VALUES ('12', '340000', '安徽省');
INSERT INTO `province_info` VALUES ('27', '610000', '陕西省');
INSERT INTO `province_info` VALUES ('4', '140000', '山西省');
INSERT INTO `province_info` VALUES ('34', '820000', '澳门特别行政区');
INSERT INTO `province_info` VALUES ('20', '450000', '广西壮族自治区');
INSERT INTO `province_info` VALUES ('18', '430000', '湖南省');
INSERT INTO `province_info` VALUES ('25', '530000', '云南省');
INSERT INTO `province_info` VALUES ('5', '150000', '内蒙古自治区');
INSERT INTO `province_info` VALUES ('11', '330000', '浙江省');
INSERT INTO `province_info` VALUES ('28', '620000', '甘肃省');
INSERT INTO `province_info` VALUES ('30', '640000', '宁夏回族自治区');
INSERT INTO `province_info` VALUES ('31', '650000', '新疆维吾尔自治区');
INSERT INTO `province_info` VALUES ('22', '500000', '重庆市');
INSERT INTO `province_info` VALUES ('3', '130000', '河北省');
INSERT INTO `province_info` VALUES ('7', '220000', '吉林省');
INSERT INTO `province_info` VALUES ('33', '810000', '香港特别行政区');
INSERT INTO `province_info` VALUES ('29', '630000', '青海省');
INSERT INTO `province_info` VALUES ('32', '710000', '台湾省');
INSERT INTO `province_info` VALUES ('19', '440000', '广东省');

SET FOREIGN_KEY_CHECKS = 1;
