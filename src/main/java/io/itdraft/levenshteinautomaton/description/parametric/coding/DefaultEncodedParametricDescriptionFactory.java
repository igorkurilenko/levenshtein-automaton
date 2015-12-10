package io.itdraft.levenshteinautomaton.description.parametric.coding;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.itdraft.levenshteinautomaton.description.parametric.coding.util.UIntPackedArray;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains precomputed encoded parametric descriptions
 * for the Levenshtein-automaton of the first and second degrees,
 * including and excluding transposition as a primitive edit operation.
 */
public class DefaultEncodedParametricDescriptionFactory implements EncodedParametricDescriptionFactory {

    protected static final EncodedParametricDescription[] ENCODED_PARAMETRIC_DESCRIPTIONS = new EncodedParametricDescription[]{
            new EncodedParametricDescription(1, false,
                    new UIntPackedArray(3, new long[]{0xbb6d56922db55b69L, 0x6ab6d45220924526L, 0x848249051ae93593L, 0x11a20a444L}),
                    new UIntPackedArray(2, new long[]{0x5554a00001500000L, 0x55557a0283c00015L, 0x155555L}),
                    new int[]{0, 1, 0, -1, -1}),
            new EncodedParametricDescription(1, true,
                    new UIntPackedArray(3, new long[]{0x6cb6c48db6cb6db1L, 0x8a488251231273dbL, 0x473c49cc93b2db6cL, 0x829222d84825120aL, 0x2c88L}),
                    new UIntPackedArray(2, new long[]{0x52a0000015000000L, 0xa02a0fc000055555L, 0x555555555555eL}),
                    new int[]{0, 1, 0, -1, -1, -1}),
            new EncodedParametricDescription(2, false,
                    new UIntPackedArray(5, new long[]{
                            0xef7bdef7bdef79c1L, 0xdef7bdef7bdef7bdL, 0xbdef7bcf78bdef7bL, 0x7bdef7bdef7bdef7L, 0xf0420f7bdef7bdefL,
                            0xe73bdef7bdef7bdeL, 0xdef7bdef7bdef7bdL, 0xbdef3ddef78a578bL, 0x7bdef7bdef7bdef7L, 0x779de39ca3f17defL,
                            0xef7bde77bdef7bc5L, 0x21083c5f7bdef7bdL, 0x9cef7bdef05fe778L, 0x7def7bdef7bdef3L, 0xf7bc17f9fe10820fL,
                            0xef7bdef7bce7bbdeL, 0xfef14c578bc1f7bdL, 0xbdef7bde7bfdef3dL, 0x19e4719eef7bdef7L, 0xf7a4ff7bd20b9c6dL,
                            0xa0bbdef3bdef7bceL, 0xdef15ee71ce728ddL, 0xbdef7bcef79de297L, 0xbde7d9ca3719eff7L, 0xf3bce778c5f7bc60L,
                            0x1081da0bfdef3bdeL, 0xce087def04ae7050L, 0xb0f7bcef7bce739L, 0x7bc83c208e202082L, 0xf43def39d073901fL,
                            0xf788420821c3c3deL, 0xcf739ee087def04aL, 0x820820a5f7bcef7bL, 0xb901f7bc8394a218L, 0x397def43def3dd07L,
                            0xef3dfef14c578a1cL, 0xdef7bdef7bde7bdfL, 0xbc9515e2798aff7bL, 0x39def7ab52bbcf2bL, 0x7902a385ce73bdefL,
                            0xe93c2e7482e71b46L, 0x2ff3bcef3bdef3bdL, 0xdd20bdcad19e47e8L, 0x3fcef39cef7b350bL, 0x71ce728c3a105cf7L,
                            0xef79de295ee715eeL, 0xa3798a577bde77bcL, 0x9292c1c52c1cb59cL, 0x9e0e739def41de77L, 0x7982f79f6728c2a3L,
                            0xe73bcef39de3142fL, 0xebd9ca37e82573bcL, 0x1ce77949095e6095L, 0x803a11e0f73dcef4L, 0x84ae704ae705010L,
                            0x6739ce739ce739ceL, 0xc1345cc68420b20dL, 0x5ce7462e7398c345L, 0x2388083add76318cL, 0x741ce404f0820f08L,
                            0xb70f6741d0741ceL, 0xac3da083da0de202L, 0x6368d9d074630739L, 0x84af78842083bcdfL, 0x7b9cf739ee084af7L,
                            0x420820b20c67b9ceL, 0x2e7b98c349e1349eL, 0x3add6a518c5ee74aL, 0x4e52a0e52886208L, 0x7c1d07c1cf741ee4L,
                            0x8398a418820b70e6L, 0xf074a307b9ac398aL, 0x4c578bbcdea568d9L, 0x7bde7bdfef3dfef1L, 0x798aff7bdef7bdefL,
                            0xf939cf7fbc5315e2L, 0xce779ce77bdef79eL, 0xcaef2545788a6315L, 0x7bce77bdead4aef3L, 0x515e20a8e1739cefL,
                            0xef7ab533dcf2bbc9L, 0x2a385ef7b9ce739dL, 0xc2e7482e71b46790L, 0x3bcef3bdef3bde93L, 0xb9c6d19e47e82ffL,
                            0x77bcef7a4f44212L, 0x46790ba415d0739dL, 0xbdecd42f7482f72bL, 0xe84173dcff3bce73L, 0x416120bdcad19e40L,
                            0x57bdd0739cef7b35L, 0xee71ce728c3a105eL, 0xbcef79de295ee715L, 0x9ca3798a577bde77L, 0x778a5946257b9c73L,
                            0x631a2e77a2e73bdeL, 0xb0714b072d6728caL, 0x9ce77bd0779de4a4L, 0xc1cb59ca30a8e783L, 0x741de7792935a252L,
                            0x728c2a39ecf7ba2eL, 0xde3142f7982f79f6L, 0x82573bce73bcef39L, 0x4a460bde7d9ca37eL, 0x73a3073bce778c54L,
                            0x57af6728cba41a30L, 0xd0739de524257982L, 0xca30e84783dcf73bL, 0x794941a46095ebd9L, 0x11ec57be30741ce7L,
                            0xe704ae705010803aL, 0xce739ce739ce084aL, 0x9c140420b20d6739L, 0xb9ce738214ce612bL, 0x8308562739ba739L,
                            0xe630d1704d1731a1L, 0x5d8c631739d18b9cL, 0xe61345cc68420eb7L, 0xc6739c62e7398c55L, 0x82388083add76f7bL,
                            0xe741ce404f0820f0L, 0x20b70f6741d0741cL, 0x9015d2883c208e20L, 0xe2749c2749b9d073L, 0x820f6837880831c5L,
                            0x6741d18c1ce6b0f6L, 0xde2020ef37d8da3L, 0x630739ac5e2883daL, 0x83bcdf6f8bda749cL, 0x84af784af788420L,
                            0x67b9ce7b9cf739eeL, 0xa12bde210820b20cL, 0xab39bdce7b8214d6L, 0x90820830852ab39bL, 0x8b9ee630d2784d27L,
                            0xeb75a946317b9d2L, 0x8c566a1349e42082L, 0xb37bc6b39ca2e7b9L, 0xa0e528862083add6L, 0x7c1cf741ee404e52L,
                            0x218820b70e67c1d0L, 0xd07b9015a528394aL, 0x31c5aab49c2b49bdL, 0xb0e62a0e62906208L, 0x95a367c1d28c1ee6L,
                            0x8398a418820ef37aL, 0xb49ca307b9ac5a92L, 0x3bcdeb38bdaL}),
                    new UIntPackedArray(3, new long[]{
                            0x0L, 0x0L, 0x490000000000000L, 0x240000000L, 0x1000000000L,
                            0x4100000000000000L, 0x400000L, 0x4900004820924L, 0x12082490400000L, 0x1000000001240L,
                            0x1000L, 0x6d80006130L, 0x24000186c00c0003L, 0x1200000201000001L, 0x834000006900006L,
                            0x492492586800c0L, 0x4924900100124924L, 0x400492490012492L, 0x9240049249249240L, 0x4924924900100124L,
                            0x2400400492490012L, 0x10010000009L, 0x50000061300000L, 0x205249001200005L, 0x30c00c001b61b600L,
                            0x6d86d800061b0cL, 0x4000207238c31230L, 0x2400402010012412L, 0xa040049049000061L, 0x41a4000204949100L,
                            0x61a4c34c20d001aL, 0xd30a340069069000L, 0x2492492492606938L, 0x9249249249249249L, 0x4924924924924924L,
                            0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x1000000924924924L, 0x6130000000000100L,
                            0xda00000568400000L, 0x140140000184eda2L, 0x8149240048000L, 0x492db20000528500L, 0xc001b61b60002052L,
                            0x6d800061b0c30c00L, 0x86ebaeba030006d8L, 0xc48c001b61b60001L, 0x6d86d800081c8e30L, 0x207238ebb23000L,
                            0x40201001241240L, 0x4004904900006124L, 0x2400018495a15a80L, 0x1252440281001241L, 0xaa04004904900008L,
                            0xa41a400020494915L, 0x61a4c34c20d001L, 0xad3a834006906900L, 0x1a41a400018693L, 0x9000081a4e34c28dL,
                            0x6938d3aa34006906L, 0x9249249249249260L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                            0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L,
                            0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x924924924L}),
                    new int[]{0, 1, 0, -1, -1, 0, -1, -1, 0, -2, -2, -2, -1, -1, 2, 1, 0, -1, -1, -2, -2, -2, -1, -2, -2, -2, -2, -2, -1, -2}),
            new EncodedParametricDescription(2, true,
                    new UIntPackedArray(6, new long[]{
                            0xaaaaaaaaaaaaa381L, 0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL, 0x2aaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaa8f3cL,
                            0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL, 0x40aaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaa810L, 0xaaaaaaaaaa38eaaaL,
                            0xaaaaaaaaaaaaaaaaL, 0x3c2aaaaaaaaaaaaaL, 0xaaaaa8f3aaaaa145L, 0xaaaaaaaaaaaaaaaaL, 0xaaaaaaaaaaaaaaaaL,
                            0x76c3aaaaaaa85a85L, 0xaaaaaa9b3aa3aa1cL, 0xaaaaaaaaaaaa3aaaL, 0x7aaaaaaaaa3aaaaaL, 0x81040aaaaaaa9ba8L,
                            0xaaaaaaa813ea3aa0L, 0xaaaaaaaaaaa8e38eL, 0x81aaaaaaaaa3eaaaL, 0x82040aaaaaaa81aL, 0xeaaaaaaa813ea3eaL,
                            0xaaaaaaaaaaaa8e3cL, 0xa82aaaaaaaaa3eaaL, 0x55c53c2aaaaaaa81L, 0xaa3cfaaaa8f3eaa8L, 0xaaaaaaaaaaaaaaaaL,
                            0x7a973ceaaaaaaaaaL, 0x868063c4a8ea8e3dL, 0xaaa48faaaa9204e3L, 0xaaaa38eaaaaaaa8eL, 0xa0aa004ea8eaaa3aL,
                            0x3871c76c3a8ea8e4L, 0xa3aa6dbaaaa9b3cfL, 0xaaaaa3aaaaaa8eaaL, 0x79ea873cfa8eaaa3L, 0xf3c78476c3a8ea8fL,
                            0x8e3aa79baaaa9e04L, 0x3eaaaa3ceaaaa8eaL, 0xf7a6aa104fa8faaaL, 0x4e381081040a8ea8L, 0x38e38e041aaaa811L,
                            0xa3eaaaa38eaaaa8eL, 0x85048a88150a8faaL, 0x1d0408888040a90aL, 0xe39038e201aaaa88L, 0xaa16aaaa410aaaa8L,
                            0xa85224aa41d0a85aL, 0x11453c2082040a90L, 0x8f38e3ce041aaaa8L, 0xaaa3eaaaa3ceaaaaL, 0xa85208a82145a8fL,
                            0x881c5142882040a9L, 0xa8f3903ce201aaaaL, 0x5aaa16aaaa150aaaL, 0x90a85224aa21c5a8L, 0xa8f3eaa855c53c2aL,
                            0xaaaaaaaaaa3cf3eaL, 0xaaaaaaaaaaaaaaaaL, 0xa8fa8f3d7a9714faL, 0xea8f6cea898c53c2L, 0xaaa8e3aaaaa5556cL,
                            0x38e3aaa8e38e38eaL, 0x43813815633a31c1L, 0x4e39204e3868063cL, 0xa8eaaaa8eaaa48f0L, 0xf38ea8e38e3aa38eL,
                            0xc43cf3cf4a03e004L, 0x4f39204f38a8063L, 0xfa8ea8e38eaaa755L, 0x813cf38e38f3ce3cL, 0x6c33c13c17603e00L,
                            0xb3cf39b3cf3871c7L, 0xaa3aaa8eaaa3aa6dL, 0x14538e38e38eaaa3L, 0x76c3a8f38579e387L, 0x1c6c539b6c538b2cL,
                            0x40e3aaa903aa3aa7L, 0xb1c741040e39038eL, 0x476c33814077e740L, 0x79b04f3de04f3c78L, 0xa3ce38ea8ea8e3aaL,
                            0x610453cf38f3cf3aL, 0x8476c33cf1457a61L, 0xa7dc0453de0453cbL, 0xce14f38ea9038e3aL, 0x16108714540f3c53L,
                            0x10810403c11477e6L, 0x8e04114e38114e38L, 0x38e38e38e38e38e3L, 0x83885d63cf38f3ceL, 0x8c1010403963d704L,
                            0x38e30c1913811913L, 0x145145138e39144eL, 0x2546582849244f3dL, 0x4088880404684a03L, 0x38e2011d04081d0L,
                            0x5040e41039038e39L, 0x2245a41d61453851L, 0x640d8880405965d7L, 0x5038e34c1d64081dL, 0x1565915963903914L,
                            0x3645a48685d7445L, 0x453c20820405a85eL, 0x38e3ce0411453c11L, 0xf3cf38e3ce3ce38fL, 0xd72083c25d73cf3cL,
                            0x1973c41020403963L, 0x244e3ce30c1973c1L, 0x8f3d24514913ce39L, 0x4a03654848204924L, 0x81c5142882040468L,
                            0x8f3903ce2011c514L, 0x3c514540e1503d03L, 0x65d72245e21d7145L, 0x481d714488204059L, 0x3924503ce34c1d71L,
                            0x74851575915d63d0L, 0xa85e03645e28605dL, 0xa8f3eaa855c53c25L, 0xaaaaaaaaaa3cf3eaL, 0xaaaaaaaaaaaaaaaaL,
                            0xa8fa8f3d7a9714faL, 0xe38f3eaa855c53c2L, 0x8e3aaaaaaaa3cf48L, 0x3aa38ea8e38eaaa3L, 0x239b39b3d739719bL,
                            0xcea8f6cea898c53cL, 0xaaaa8e3aaaaa5556L, 0x138e3aaa8e38e38eL, 0xc23813815633a31cL, 0x78f38f6cea898c53L,
                            0xe38e38e3aaaaa555L, 0xc13ce3cea8f3cf38L, 0x3c43c13c15633e31L, 0xf04e39204e386806L, 0x8ea8eaaaa8eaaa48L,
                            0x4f38ea8e38e3aa3L, 0x63c43cf3cf4a03e0L, 0x8f21041204e38680L, 0x38e3903aaa8eaaa4L, 0x21b40e39039040eL,
                            0x63c415b15b4a016L, 0x75504f39204f38a8L, 0xe3cfa8ea8e38eaaaL, 0xe00813cf38e38f3cL, 0x8063c43c13c17603L,
                            0xa75520541204f38aL, 0x4f3cf39038e38eaaL, 0x16008114f3d03851L, 0x71c76c3141141760L, 0xaa6db3cf39b3cf38L,
                            0xaaa3aa3aaa8eaaa3L, 0xe38714538e38e38eL, 0x871c76c3a8f38579L, 0x3aa6db49245b3cf3L, 0x138e3aa44e38eaaaL,
                            0x9e44718644e45139L, 0x38b2c76c339b4467L, 0xa3aa71c6c539b6c5L, 0x9038e40e3aaa903aL, 0x7e740b1c741040e3L,
                            0x538b2c76c3381407L, 0xaa3aa71c79745b6cL, 0x3963cf40e44e3903L, 0x77e758b1c7590591L, 0x4f3c78476c33c158L,
                            0xa8e3aa79b04f3de0L, 0xf3cf3aa3ce38ea8eL, 0x457a61610453cf38L, 0x4f3c78476c33cf1L, 0xea8e3aa79b21249eL,
                            0x523d240e3ce45038L, 0x5c67a65e120648f4L, 0xe0453cb8476c315bL, 0x9038e3aa7dc0453dL, 0x40f3c53ce14f38eaL,
                            0x11477e6161087145L, 0x9e0453cb8476c33cL, 0x39038e3aa7dc2174L, 0x55923d714f14f450L, 0x415c77e65e10875cL,
                            0x38114e3810810401L, 0xe38e38e38e04114eL, 0xcf38f3ce38e38e38L, 0x3963d70483885d63L, 0x34c114e381081040L,
                            0xd34ce38e38e04125L, 0x54f4d53d34d338e4L, 0x4e95630484c88e9L, 0x9138119138c10104L, 0x38e39144e38e30c1L,
                            0x849244f3d1451451L, 0x404684a032546582L, 0x2984c119138c1010L, 0x14d34d144e38e30cL, 0x287526153d861845L,
                            0x406287603256258L, 0x11d04081d0408888L, 0x1039038e39038e20L, 0x1d614538515040e4L, 0x80405965d72245a4L,
                            0x12d45081d040888L, 0x4104d44ce39038e2L, 0x42e92454c9154513L, 0x880406696a322466L, 0x34c1d64081d640d8L,
                            0x159639039145038eL, 0xa48685d744515659L, 0x8880405a85e03645L, 0xe34c2d95081d640dL, 0x585964d44d145038L,
                            0x6648686976091596L, 0x20820406686a0364L, 0xce0411453c11453cL, 0x38e3ce3ce38f38e3L, 0x83c25d73cf3cf3cfL,
                            0xc20820403963d720L, 0x3ce0412495411453L, 0x54d33ce5534cf38eL, 0x85428e354f5553dL, 0x3c41020404e95632L,
                            0xe3ce30c1973c1197L, 0xd24514913ce39244L, 0x36548482049248f3L, 0x73c41020404684a0L, 0x4e3ce30c29a54119L,
                            0x3dd6184915534d24L, 0x365744820752755L, 0xc514288204062876L, 0x3903ce2011c51481L, 0x514540e1503d038fL,
                            0xd72245e21d71453cL, 0x1c51428820405965L, 0xf3903ce2012c9248L, 0x491495131505544cL, 0x6a32246a22e32455L,
                            0x81d7144882040669L, 0x924503ce34c1d714L, 0x4851575915d63d03L, 0x85e03645e28605d7L, 0x481d71448820405aL,
                            0x4d24503ce34c2da2L, 0x774915a6585d6554L, 0x686a03646a286069L, 0x6L}),
                    new UIntPackedArray(3, new long[]{
                            0x0L, 0x0L, 0x0L, 0x9000000000000000L, 0x24000000004L,
                            0x0L, 0x10000L, 0x0L, 0x4000004100L, 0x9240000000000400L,
                            0x4900004820L, 0x2490000410000080L, 0x1240001208L, 0x104000020L, 0x100L,
                            0x80000c30004c0000L, 0x6c00030000006dL, 0x4000030c001b0c06L, 0x10002010000012L, 0x900000c200048201L,
                            0x4004c00834000006L, 0x924924308001a080L, 0x800900124924004L, 0x2492490412412410L, 0x200240049249001L,
                            0x4924924104904904L, 0x80090012492400L, 0x1249249041241241L, 0x4020024004924900L, 0x100000010490490L,
                            0x10L, 0x5000000c30004cL, 0x9201249001200005L, 0xb61b600024900814L, 0xdb0db61b0c00c001L,
                            0x6d86d80006db0186L, 0xc6e36dc6c3123000L, 0x1241240001c70061L, 0x4924920104020100L, 0x4904900000d2010L,
                            0x124924a45100a040L, 0x1a41a4000244804L, 0x6924926134c20d0L, 0x6906900006d201L, 0x41a49249c4d30a34L,
                            0x924924924925c480L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L,
                            0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                            0x9249249249249249L, 0x1001000000L, 0xc30004c00000000L, 0xda00000568400000L, 0x2bac0a13a2d16d02L,
                            0x48000140140000L, 0x924020524804924L, 0x2db2000052850000L, 0x249008149251249L, 0xb0c00c001b61b600L,
                            0x6db0186db0db61L, 0x6eba030006d86d80L, 0x1b6c061b6eb6ddL, 0x1b0c48c001b61b60L, 0x71c01871b8db7L,
                            0xc6ebb230006d86d8L, 0x1c70061c6e36dL, 0x104020100124124L, 0xd2010492492L, 0xad415a8040049049L,
                            0x40002b4804124924L, 0x2914402810012412L, 0x9000091201049249L, 0x4a4515aa04004904L, 0xa400024480412492L,
                            0x926134c20d001a41L, 0x6900006d20106924L, 0x249d4d3a83400690L, 0x1a40001b48041a49L, 0x4927134c28d001a4L,
                            0x690000712010692L, 0x9249c4d3aa340069L, 0x24924925c48041a4L, 0x9249249249249249L, 0x4924924924924924L,
                            0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                            0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L,
                            0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L,
                            0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L,
                            0x9249249249249249L, 0x4924924924924924L, 0x2492492492492492L, 0x9249249249249249L, 0x0L}),
                    new int[]{0, 1, 0, -1, -1, 0, -1, -1, 0, -2, -2, -2, -1, -1, 2, 1, 0, -1, -1, -2, -2, -2, -1, -1, -2, -2, -2, 0, -2, -2, -1, -2, -2, -2, -1, -2, -1, -1, -2, -2, -2, -2})
    };
    protected static final Map<Integer, EncodedParametricDescription> EXCL_TRANSPOSITION_BY_DEGREE = new HashMap<>();
    protected static final Map<Integer, EncodedParametricDescription> INCL_TRANSPOSITION_BY_DEGREE = new HashMap<>();

    static {
        for (EncodedParametricDescription description : ENCODED_PARAMETRIC_DESCRIPTIONS) {
            if (description.doesInclTransposition()) {
                INCL_TRANSPOSITION_BY_DEGREE.put(description.getAutomatonDegree(), description);

            } else {
                EXCL_TRANSPOSITION_BY_DEGREE.put(description.getAutomatonDegree(), description);
            }
        }
    }

    /**
     * Returns precomputed encoded parametric description of the Levenshtein-automaton
     * for the specified parameters.
     *
     * @param degree            a degree of the Levenshtein-automaton.
     *                          {@code DefaultEncodedParametricDescriptionFactory}
     *                          returns description if {@code degree} is equal to {@code 1}
     *                          or {@code 2} only.
     * @param inclTransposition specifies whether parametric description is for the
     *                          Levenshtein-automaton which support transposition
     *                          as a primitive edit operation.
     */
    public EncodedParametricDescription getEncodedParametricDescription(int degree, boolean inclTransposition) {
        return inclTransposition ? getInclTransposition(degree) : getExclTransposition(degree);
    }

    private EncodedParametricDescription getExclTransposition(int automatonDegree) {
        return EXCL_TRANSPOSITION_BY_DEGREE.get(automatonDegree);
    }

    private EncodedParametricDescription getInclTransposition(int automatonDegree) {
        return INCL_TRANSPOSITION_BY_DEGREE.get(automatonDegree);
    }
}
