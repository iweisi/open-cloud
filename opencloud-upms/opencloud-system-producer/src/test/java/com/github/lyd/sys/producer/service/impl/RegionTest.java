package com.github.lyd.sys.producer.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyadu
 */
public class RegionTest {
    private static DriverManagerDataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    public RegionTest() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/open-platform?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {
        RegionTest demo = new RegionTest();
        List<Regions> list = demo.BufferedReaderDemo("C:\\Users\\admin\\Desktop\\新建文本文档 (3).txt");
        List<String> sqls = Lists.newArrayList();
        for (Regions area :
                list) {
            System.out.println(area);
            String sql = String.format("INSERT INTO china_regions (code, parent_code, name, status, province, city,level) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", area.getCode(), area.getParentCode(), area.getName(), 1, area.getProvince(), area.getCity(), area.getLevel());
            sqls.add(sql);
        }
        String[] batch = new String[sqls.size()];
        sqls.toArray(batch);
        jdbcTemplate.batchUpdate(batch);
    }

    public List<Regions> BufferedReaderDemo(String path) {
        String line = null;
        BufferedReader reader = null;
        File file = new File(path);

        String cityCode = "";
        String city = "";
        String provinceCode = "";
        String province = "";
        List<Regions> result = new ArrayList<Regions>();
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        try {
            FileReader in = new FileReader(file);
            reader = new BufferedReader(in);
            //读取文件的每一行
            while ((line = reader.readLine()) != null) {
                String[] data = this.getLine(line);
                //处理读取的文件记录
                if (this.isProvince(data[0])) {
                    provinceCode = data[0];
                    province = data[1];
                    cityCode = "";
                    city = "";
                    Regions area = new Regions(provinceCode, province, 1, "0", "", "");
                    result.add(area);
                } else if (this.isCity(data[0])) {
                    cityCode = data[0];
                    city = data[1];
                    Regions area = new Regions(cityCode, city, 2, provinceCode, province, city);
                    result.add(area);
                } else {

                    if (StringUtils.isBlank(city) || !(cityCode.substring(0, 4) + "00").equals(data[0].substring(0, 4) + "00")) {
                        provinceCode = data[0].substring(0, 3) + "000";
                        cityCode = data[0].substring(0, 4) + "00";
                        city = province;
                        Regions area = new Regions(cityCode, "市辖区", 2, provinceCode, province, city);
                        result.add(area);
                    }
                    Regions area = new Regions(data[0], data[1], 3, cityCode, province, city);
                    result.add(area);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

    }


    public String[] getLine(String line) {
        String code = "";
        String name = "";
        code = line.substring(0, 6);
        name = line.substring(6, line.length());
        String[] result = new String[]{code, name};
        return result;
    }

    /**
     * 判断是否省或者直辖市
     */
    public boolean isProvince(String code) {
        String last = code.substring(2);
        if ("0000".equalsIgnoreCase(last)) {
            return true;
        }
        return false;

    }

    /**
     * 判断是否地级市
     *
     * @param code
     * @return
     */
    public boolean isCity(String code) {
        String last = code.substring(4);
        if ("00".equalsIgnoreCase(last)) {
            return true;
        }
        return false;
    }


    /**
     * @author liuyadu
     */
    public static class Regions implements Serializable {
        private static final long serialVersionUID = 7911280928406428912L;
        private String code;
        /**
         * 行政编码
         */
        private String name;
        /**
         * 名称
         */
        private int level;
        /**
         * 行政级别 0:省/直辖市 1:地级市 2:县级市
         */
        private String parentCode;

        /**
         * 城市
         */
        private String city;

        /**
         * 省份
         */
        private String province;
        //上一级的行政区划代码

        public Regions() {
            super();
        }

        public Regions(String code, String name, int level, String parentCode, String province, String city) {
            super();
            this.code = code;
            this.name = name;
            this.level = level;
            this.parentCode = parentCode;
            this.province = province;
            this.city = city;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Area{");
            sb.append("code='").append(code).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", level=").append(level);
            sb.append(", parentCode='").append(parentCode).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", province='").append(province).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
 